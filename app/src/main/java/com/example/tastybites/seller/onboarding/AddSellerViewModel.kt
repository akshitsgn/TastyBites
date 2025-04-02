package com.example.tastybites.seller.onboarding



import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastybites.common.models.Seller
import com.example.tastybites.common.supabase.SupabaseStorageUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
        onError: (String) -> Unit,
        context: Context
    ) {
        if (restaurantMenu != null && restaurantImage!=null) {

            viewModelScope.launch {
                val storageUtils = SupabaseStorageUtils(context)
                val restaurantImage = storageUtils.uploadImage(restaurantImage)
                val restaurantMenu = storageUtils.uploadImage(restaurantMenu)
                val seller = seller.copy(restaurantMenu = restaurantMenu.toString(), restaurantImage = restaurantImage.toString())

                addSeller(seller, onSuccess, onError)
            }
        } else {
            // No image was selected, proceed with adding the student without image URL
            addSeller(seller, onSuccess, onError)
        }
    }

    fun updateAcceptTermsAndConditions(
        sellerId: String,
        acceptTerms: Boolean,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val updates = mapOf(
            // Only update this field
            "acceptTermsAndConditions" to acceptTerms,
            "currentStep" to 5

        )

        firebaseDatabase.getReference("seller")
            .child(sellerId)
            .updateChildren(updates)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception ->
                onError(exception.message ?: "Failed to update terms and conditions")
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

    fun updateSellerAnimationStatus(
        sellerId:String,
        status:Boolean,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        // Create a map with only the fields to update
        val updates = mapOf(
            "status" to status
        )
        if (sellerId != "") {
            firebaseDatabase.getReference("seller")
                .child(sellerId)
                .updateChildren(updates)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onError() }
        }
    }
}
