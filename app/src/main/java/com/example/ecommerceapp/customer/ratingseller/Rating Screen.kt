package com.example.ecommerceapp.customer.ratingseller

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RatingScreen(){
    val context = LocalContext.current
    val viewModel: RatingViewModel= hiltViewModel()
    var rating by remember {
    mutableStateOf("")
}
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(value = rating, onValueChange = {
            rating = it
        },
            label = { Text("rating") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            viewModel.addSellerRating(rating.toInt(), onError = {
                Toast.makeText(context,"error occured ",Toast.LENGTH_SHORT).show()
            }, onSuccess = {
rating=""
                Toast.makeText(context,"rated successfully",Toast.LENGTH_SHORT).show()
            })
        }) {
            Text("Submit rating")
        }
    }
}