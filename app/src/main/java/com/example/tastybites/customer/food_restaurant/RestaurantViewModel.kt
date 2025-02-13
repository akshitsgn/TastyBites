package com.example.tastybites.customer.food_restaurant


import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tastybites.common.models.Seller
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
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
class RestaurantListViewModel @Inject constructor() : ViewModel() {

    private val firebaseDatabase = Firebase.database
    private val _restaurants = MutableStateFlow<List<Seller>>(emptyList())
    val restaurants = _restaurants.asStateFlow()


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
}