package com.example.ecommerceapp.customer.Feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerceapp.common.models.Reviews
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RatingViewModel @Inject constructor() : ViewModel() {
    private val dbReference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Function to add a rating for a seller
    fun addSellerRating(review: Reviews , onError: () -> Unit, onSuccess: () -> Unit,  sellerId: String) {
            // Generate a new key for the rating
            val ratingKey = dbReference.child("sellerRatings").child(sellerId).push().key

            if (ratingKey != null) {
                // Store the rating under the seller's node
                dbReference.child("sellerRatings").child(sellerId).child(ratingKey)
                    .setValue(review)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onSuccess()
                        } else {
                            onError()
                        }
                    }
            }

    }
}
