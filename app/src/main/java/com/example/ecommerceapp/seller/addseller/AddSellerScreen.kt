package com.example.ecommerceapp.seller.addseller
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerceapp.common.models.FoodItems
import com.example.ecommerceapp.common.models.Seller

@Composable
fun AddSellerScreen(){

    val viewModel: AddSellerViewModel = hiltViewModel()
    val context = LocalContext.current
    var name by remember {
        mutableStateOf("")
    }
    var address by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var imageUri by remember {
        mutableStateOf("")
    }
    var restaurantMenu by remember {
        mutableStateOf("")
    }

        val seller= Seller(
            name = name,
            address = address,
            description = description,

        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center

    ) {
        // Name TextField
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Seller Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Type TextField
        TextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Seller Address") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Description TextField
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Seller Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
       Button(onClick = {
viewModel.addSeller(seller, onError = {}, onSuccess = {
    name=""
    description=""
    address=""
})
        },
            modifier = Modifier.fillMaxWidth()
        ) {
Text("Add Seller")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Check(){
    AddSellerScreen()
}