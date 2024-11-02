package com.example.ecommerceapp.customer.ratingseller

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
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
import com.example.ecommerceapp.common.models.Reviews

@Composable
fun RatingScreen(){
    val context = LocalContext.current
    val viewModel: RatingViewModel= hiltViewModel()
    var rating by remember {
    mutableStateOf("")
    }
    var ratingCategory by remember {
        mutableStateOf("")
    }

    var description by remember{
        mutableStateOf("")
    }

    var review = Reviews(
         description = description,
         rating = rating,
         ratingCategory = ratingCategory

    )
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(WindowInsets.statusBars.asPaddingValues())) {
        TextField(value = rating, onValueChange = {
            rating = it
        },
            label = { Text("rating") },
           // keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            viewModel.addSellerRating(review, onError = {
                Toast.makeText(context,"error occured ",Toast.LENGTH_SHORT).show()
            },
                onSuccess = {
                         rating=""
                ratingCategory=""
                description=""
                Toast.makeText(context,"rated successfully",Toast.LENGTH_SHORT).show()
            },
                "KcnevBuVnjVGrMQ6wl5EupJKApv2")
        }) {
            Text("Submit rating")
        }
    }
}