package com.example.tastybites.seller.added_products



import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tastybites.common.models.FoodItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SellerProductsViewModel @Inject constructor() : ViewModel() {

    private val dbReference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val sellerId = auth.currentUser?.uid

    private val _productList = MutableStateFlow<List<FoodItems>>(emptyList())
    val productList = _productList.asStateFlow()

    private var productListener: ValueEventListener? = null


    init{
        ListeningToProducts()
    }



    // Function to start listening for product changes
    fun ListeningToProducts() {
        if (sellerId != null && productListener == null) {
            productListener = dbReference.child("products").child(sellerId).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val productList = mutableListOf<FoodItems>()
                    for (productSnapshot in snapshot.children) {
                        val product = productSnapshot.getValue(FoodItems::class.java)
                        if (product != null) {
                            productList.add(product)
                        }
                    }
                    _productList.value = productList  // Update LiveData with new product list
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }
}
