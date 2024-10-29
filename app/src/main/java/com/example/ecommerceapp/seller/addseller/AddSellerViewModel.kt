package com.example.ecommerceapp.seller.addseller


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

    fun uploadImageAndAddSeller(
        menuImage: Uri?,
        seller: Seller,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (menuImage != null) {
            val imageRef = firebaseStorage.child("images/${UUID.randomUUID()}.jpg")
            imageRef.putFile(menuImage)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        // Only update the restaurantMenu field
                        val sellerId = currentUser?.uid
                        if (sellerId != null) {
                            val sellerRef = FirebaseDatabase.getInstance().reference.child("seller").child(sellerId)

                            sellerRef.child("restaurantMenu").setValue(uri.toString())
                                .addOnSuccessListener {
                                    onSuccess()
                                }
                                .addOnFailureListener {
                                    onError(it.message ?: "Failed to update restaurant menu")
                                }
                        } else {
                            onError("Seller ID not found")
                        }
                    }
                }
                .addOnFailureListener {
                    onError(it.message ?: "Failed to upload image")
                }
        } else {
            // If no image is provided, just add the seller details
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
            "FSSAIRegNumber" to fSSAINumber,
            "GSTIN" to gSTINNumber,
            "panNumber" to pANNumber,
            "IFSCNumber" to ifsc,
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
