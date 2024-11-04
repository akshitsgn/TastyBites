package com.example.ecommerceapp.seller.addproduct

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerceapp.common.models.FoodItems
import com.example.ecommerceapp.common.models.Seller
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
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
        onError: (String) -> Unit
    ) {
        if (restaurantFood != null ) {
            val imageRef = firebaseStorage.child("imagesFood/${UUID.randomUUID()}.jpg")
            imageRef.putFile(restaurantFood)
                .addOnSuccessListener { taskSnapshot ->
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        // Set the imageUrl with the Firebase Storage download URL
                        val restaurantFoodImage = food.copy(imageUrl = uri.toString())
                        // Now add the student with the image URL to the database
                        addProduct(restaurantFoodImage, onSuccess, onError)
                    }
                }

                .addOnFailureListener {
                    onError(it.message ?: "Failed to upload image")
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
}