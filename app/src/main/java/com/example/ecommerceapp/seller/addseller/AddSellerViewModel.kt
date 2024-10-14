package com.example.ecommerceapp.seller.addseller


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
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddSellerViewModel @Inject constructor() : ViewModel() {
    private val firebaseDatabase = Firebase.database
    private val _users = MutableStateFlow<List<Seller>>(emptyList())
    val users = _users.asStateFlow()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    init {
        listenToUserChanges()
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
}
