package com.example.tastybites.seller.add_product


import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastybites.common.models.FoodItems
import com.example.tastybites.common.supabase.SupabaseStorageUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddFoodViewModel @Inject constructor(): ViewModel(){
    private val firebaseStorage = FirebaseStorage.getInstance().reference
    private val dbReference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val sellerId = auth.currentUser?.uid
    private val _productList = MutableLiveData<List<FoodItems>>()
    val productList: LiveData<List<FoodItems>> = _productList

    init {
        listenToProductChanges()
    }
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

    fun uploadFood(
        restaurantFood: Uri?,
        food: FoodItems,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
        context: Context
    ) {
        if (restaurantFood != null ) {
            viewModelScope.launch {
                val storageUtils = SupabaseStorageUtils(context)
                val foodImage = storageUtils.uploadImage(restaurantFood)
                val restaurantFoodImage = food.copy(imageUrl = foodImage.toString())
                addProduct(restaurantFoodImage, onSuccess, onError)
            }
        } else {
            // No image was selected, proceed with adding the student without image URL
            addProduct(food, onSuccess, onError)
        }
    }


    fun addProduct(product: FoodItems, onSuccess:()-> Unit, onError:(String)-> Unit) {
        // Get the authenticated user's UID
        if (sellerId != null) {
            val productId = dbReference.child("products").child(sellerId).push().key
            val updatedProduct = product.copy(productId = productId.toString())
            if (productId != null) {
                dbReference.child("products").child(sellerId).child(productId).setValue(updatedProduct)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onSuccess()
                        } else {
                            onError("Error Occurred")
                            // Handle failure
                        }
                    }
            }
        }
    }
    fun addProductWithId(product: FoodItems, onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (sellerId != null) {
            val productId = product.productId
            if (productId.isNotEmpty()) {
                val updatedProduct = product.copy(productId = productId)
                dbReference.child("products").child(sellerId).child(productId).setValue(updatedProduct)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onSuccess()
                        } else {
                            onError("Error Occurred")
                        }
                    }
            } else {
                onError("Product ID is missing")
            }
        }
    }
    fun deleteProduct(productId: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (sellerId != null) {
            dbReference.child("products").child(sellerId).child(productId).removeValue()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        onError("Error Occurred while deleting the product")
                    }
                }
        }
    }

}