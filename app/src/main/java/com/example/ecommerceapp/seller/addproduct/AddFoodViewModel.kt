package com.example.ecommerceapp.seller.addproduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerceapp.common.models.FoodItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddFoodViewModel @Inject constructor(): ViewModel(){
    private val dbReference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
private val sellerId = auth.currentUser?.uid
    private val _productList = MutableLiveData<List<FoodItems>>()
    val productList: LiveData<List<FoodItems>> = _productList

    fun listenToProductChanges() {
        val currentUser = auth.currentUser
        val sellerId = currentUser?.uid  // Get the current user's UID

        if (sellerId != null) {
            dbReference.child(sellerId).child("products").addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val productList = mutableListOf<FoodItems>()
                    for (productSnapshot in snapshot.children) {
                        val product = productSnapshot.getValue(FoodItems::class.java)
                        if (product != null) {
                            productList.add(product)
                        }
                    }
                    _productList.value = productList  // Update LiveData
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error if needed
                }
            })
        }
    }

    fun addProduct(product: FoodItems, onSuccess:()-> Unit, onError:()-> Unit) {
          // Get the authenticated user's UID

        if (sellerId != null) {
            val productId = dbReference.child("products").child(sellerId).push().key
            if (productId != null) {
                dbReference.child("products").child(sellerId).child(productId).setValue(product)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onSuccess()
                        } else {
                            onError()
                            // Handle failure
                        }
                    }
            }
        }
    }
}