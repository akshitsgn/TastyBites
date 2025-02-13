package com.example.tastybites.common.screens


import androidx.lifecycle.ViewModel
import com.example.tastybites.common.models.Buyer
import com.example.tastybites.common.models.Seller
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(): ViewModel(){

    private val sellerDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference("seller")
    private val buyerDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference("buyer")



    fun addSeller(seller: Seller, onSuccess:()-> Unit, onError:(Exception)-> Unit){
        val sellerId = sellerDatabase.push().key?:return
        val sellerWithId = seller.copy(id= sellerId)
        sellerDatabase.child(sellerId).setValue(sellerWithId)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it) }
    }

    fun addBuyer(buyer: Buyer, onSuccess: () -> Unit, onError: (Exception) -> Unit){
        val buyerId = buyerDatabase.push().key?:return
        val buyerWithId = buyer.copy(id=buyerId)
        buyerDatabase.child(buyerId).setValue(buyerWithId)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it) }
    }

}