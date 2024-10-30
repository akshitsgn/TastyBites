package com.example.ecommerceapp.seller.addseller


import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerceapp.common.models.FoodItems
import com.example.ecommerceapp.common.models.Seller
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddSellerViewModel @Inject constructor() : ViewModel() {
    private val firebaseStorage = FirebaseStorage.getInstance().reference
    private val firebaseDatabase = Firebase.database
    private val _users = MutableStateFlow<List<Seller>>(emptyList())
    val users = _users.asStateFlow()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val _uniqueSeller = MutableStateFlow<Seller?>(null)
    val uniqueSeller: StateFlow<Seller?> = _uniqueSeller
    init {
        listenToUserChanges()
        currentUser?.uid?.let { sellerId ->
            getSellerById(sellerId, onError = {}, onSuccess = {})
        }
    }

    private fun listenToUserChanges() {
        firebaseDatabase.getReference("seller").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list = mutableListOf<Seller>()
                dataSnapshot.children.forEach { data ->
                    val user = data.getValue(Seller::class.java)
                    user?.let { list.add(it) }
                }
                _users.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "Failed to read value: ${error.message}")
            }
        })
    }


    fun uploadMenuImageAndAddSeller(
        restaurantMenu: Uri?,
        restaurantImage: Uri?,
        seller: Seller,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (restaurantMenu != null && restaurantImage!=null) {
            val imageRef = firebaseStorage.child("imagesMenu/${UUID.randomUUID()}.jpg")
            imageRef.putFile(restaurantMenu)
                .addOnSuccessListener { taskSnapshot ->
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        // Set the imageUrl with the Firebase Storage download URL
                        val restaurantMenuImage = seller.copy(restaurantMenu = uri.toString())
                        // Now add the student with the image URL to the database
                        addSeller(restaurantMenuImage, onSuccess, onError)
                    }
                }

            val restaurantImageRef =  firebaseStorage.child("imagesRestaurant/${UUID.randomUUID()}.jpg")
            restaurantImageRef.putFile(restaurantImage)
                .addOnSuccessListener { taskSnapshot ->
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                                // Set the imageUrl with the Firebase Storage download URL
                        val restaurantsImage = seller.copy(restaurantImage = uri.toString())
                                // Now add the student with the image URL to the database
                        addSeller(restaurantsImage, onSuccess, onError)
                    }
                }

                .addOnFailureListener {
                    onError(it.message ?: "Failed to upload image")
                }
        } else {
            // No image was selected, proceed with adding the student without image URL
            addSeller(seller, onSuccess, onError)
        }
    }


    fun updateSellerBankDetails(
        fSSAINumber: String,
        currentStep: Int,
        gSTINNumber: String,
        pANNumber: String,
        ifsc: String,
        bankAccountNumber: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val sellerId = currentUser?.uid
        // Create a map with only the fields to update
        val updates = mapOf(
            "fssairegNumber" to fSSAINumber,
            "gstin" to gSTINNumber,
            "panNumber" to pANNumber,
            "ifscnumber" to ifsc,
            "bankAccountNumber" to bankAccountNumber ,
            "currentStep" to currentStep
        )
        if (sellerId != null) {
            firebaseDatabase.getReference("seller")
                .child(sellerId)
                .updateChildren(updates)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onError() }
        }
    }

      fun addSeller(seller: Seller, onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (currentUser == null) {
            return onError("User is not authenticated.")
        }
          val sellerId = currentUser.uid
        val sellerWithId = seller.copy(id = sellerId)
        firebaseDatabase.getReference("seller").child(sellerId).setValue(sellerWithId)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Unknown error occurred")
            }
    }

    fun getSellerById(sellerId: String, onSuccess: (Seller?) -> Unit, onError: (String)-> Unit) {    firebaseDatabase.getReference("seller").child(sellerId)
        .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val seller = snapshot.getValue(Seller::class.java)
                _uniqueSeller.value = seller
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("AddSellerViewModel", "Failed to listen for seller changes: ${error.message}")
            }
        })
    }
}
