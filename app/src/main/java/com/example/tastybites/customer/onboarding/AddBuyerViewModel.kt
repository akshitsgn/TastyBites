package com.example.tastybites.customer.onboarding

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.tastybites.common.models.Buyer
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

@Suppress("NAME_SHADOWING")
@HiltViewModel
class AddBuyerViewModel @Inject constructor(): ViewModel(){
    private val buyerDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference("buyer")
    private val firebaseStorage = FirebaseStorage.getInstance().reference
    private val firebaseDatabase = Firebase.database
    private val currentBuyer = FirebaseAuth.getInstance().currentUser
    private val _uniqueBuyer = MutableStateFlow<Buyer?>(null)
    val uniqueBuyer: StateFlow<Buyer?> = _uniqueBuyer


    init{
        currentBuyer?.uid?.let { buyerId ->
            getBuyerById( buyerId, onError = {}, onSuccess = {})
        }
    }

    fun updateBuyerLocation(
        contact: String,
        alternateContact:String,
        medicalCondition: String,
        onSuccess:()->Unit,
        onError:()-> Unit
    ){
        val buyerId= currentBuyer?.uid
        val updates = mapOf(
            "contact" to contact,
            "alternateContact" to alternateContact,
            "medicalCondition" to medicalCondition
        )
        if(buyerId!= null){
            firebaseDatabase.getReference("buyer")
                .child(buyerId)
                .updateChildren(updates)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener{onError()}
        }
    }

    fun getBuyerById(buyerId:String, onError: () -> Unit,onSuccess: () -> Unit){
        if(buyerId!="") {
            firebaseDatabase.getReference("buyer").child(buyerId)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val buyer = snapshot.getValue(Buyer::class.java)
                        _uniqueBuyer.value = buyer
                    }

                    override fun onCancelled(error: DatabaseError) {
                        onError()
                    }

                })
        }

    }

    fun addBuyer(buyer: Buyer, onSuccess: () -> Unit, onError: () -> Unit) {
        val buyerId = currentBuyer?.uid
        if (buyerId!=null) {
            val buyerWithId = buyer.copy(id = buyerId)
            buyerDatabase.child(buyerId).setValue(buyerWithId)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onError() }
        }
    }

    fun uploadBuyerImage(
        buyerImage: Uri?,
        buyer: Buyer,
        onSuccess:()-> Unit,
        onError: ()-> Unit
    ){
        if(buyerImage!= null){
            val buyerImageRef = firebaseStorage.child("imageBuyer/${UUID.randomUUID()}.jpg")
            buyerImageRef.putFile(buyerImage)
                .addOnSuccessListener { taskSnapshot->
                    buyerImageRef.downloadUrl.addOnSuccessListener { uri->
                        val buyerImage = buyer.copy(imageUri = uri.toString())
                        addBuyer(buyerImage , onSuccess , onError)
                    }
                }
        }
    }
}