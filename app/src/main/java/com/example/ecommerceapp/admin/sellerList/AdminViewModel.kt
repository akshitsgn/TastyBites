package com.example.ecommerceapp.admin.sellerList

import android.util.Log
import android.view.View
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.ecommerceapp.common.models.Seller
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class AdminViewModel @Inject constructor(): ViewModel(){

    private val firebaseDatabase = Firebase.database
    private val _restaurants = MutableStateFlow<List<Seller>>(emptyList())
    val restaurants = _restaurants.asStateFlow()
    private val _uniqueSeller =mutableStateOf<Seller?>(null)
    val uniqueSeller: State<Seller?> = _uniqueSeller

    init {
        listenToUserChanges()
    }

    private fun listenToUserChanges() {
        firebaseDatabase.getReference("seller").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list = mutableListOf<Seller>()
                dataSnapshot.children.forEach { data ->
                    val restaurant = data.getValue(Seller::class.java)
                    restaurant?.let { list.add(it) }
                }
                _restaurants.value = list
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "Failed to read value: ${error.message}")
            }
        })
    }

    fun updateSellerVerifyStatusDetails(
        sellerId:String,
        verified:Boolean,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        // Create a map with only the fields to update
        val updates = mapOf(
          "verified" to verified
        )
        if (sellerId != "") {
            firebaseDatabase.getReference("seller")
                .child(sellerId)
                .updateChildren(updates)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onError() }
        }
    }

    fun getSellerById(sellerId: String) {    firebaseDatabase.getReference("seller").child(sellerId)
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