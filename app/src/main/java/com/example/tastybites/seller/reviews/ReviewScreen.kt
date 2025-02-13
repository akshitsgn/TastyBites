package com.example.tastybites.seller.reviews


import android.media.Rating
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.tastybites.common.models.Reviews
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RatingsViewModel @Inject constructor() : ViewModel() {
    private val firebaseStorage = FirebaseStorage.getInstance().reference
    private val firebaseDatabase = Firebase.database
    private val _ratings = MutableStateFlow<List<Reviews>>(emptyList())
    val ratings= _ratings.asStateFlow()



    init {
        fetchRatings()
    }

    private fun fetchRatings() {
        firebaseDatabase.getReference("sellerRatings").child("KcnevBuVnjVGrMQ6wl5EupJKApv2").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list = mutableListOf<Reviews>()
                dataSnapshot.children.forEach { data ->
                    val user = data.getValue(Reviews::class.java)
                    user?.let { list.add(it) }
                }
                _ratings.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "Failed to read value: ${error.message}")
                // Handle possible errors.
            }
        })
    }
}