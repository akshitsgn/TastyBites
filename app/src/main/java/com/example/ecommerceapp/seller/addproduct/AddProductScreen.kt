package com.example.ecommerceapp.seller.addproduct


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.seller.addseller.AddSellerViewModel
import com.example.ecommerceapp.common.models.FoodItems
import com.example.ecommerceapp.common.models.Seller
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AddProductScreen(navController: NavController){
    val context= LocalContext.current
    val viewModel: AddFoodViewModel = hiltViewModel()
    var name by remember {
        mutableStateOf("")
    }
    var type by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var ingredients by remember {
        mutableStateOf("")
    }
    var price by remember {
        mutableStateOf("")
    }
    var discount by remember {
        mutableStateOf("")
    }
    val price1 by remember {
        mutableStateOf(0)
    }
    val discount1 by remember {
        mutableStateOf(0)
    }
    var imageUrl by remember {
        mutableStateOf("")
    }

        var check by remember {
            mutableStateOf(true)
        }

    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val sellerId= auth.currentUser?.uid

    val foodItems = FoodItems(
            productId = "", // This will be generated in the `addProductToSeller` method
            name = name,
            description = description,
            price = price1,
            discount = discount1,
            type = type,
            ingredients = ingredients
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
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Type TextField
        TextField(
            value = type,
            onValueChange = { type = it },
            label = { Text("Product Type") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Description TextField
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Product Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Ingredients TextField
        TextField(
            value = ingredients,
            onValueChange = { ingredients = it },
            label = { Text("Ingredients") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Price TextField
        TextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Discount TextField
        TextField(
            value = discount,
            onValueChange = { discount = it },
            label = { Text("Discount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {

            check= false
       viewModel.addProduct(foodItems, onSuccess = {
           Toast.makeText(context,"Added product successfully",Toast.LENGTH_SHORT).show()
           name=""
           description=""
           ingredients=""
           type=""
           price=""
           discount=""
           check = true

       }, onError = {
           Toast.makeText(context,"error adding product",Toast.LENGTH_SHORT).show()
       })

        },
            modifier=Modifier.fillMaxWidth(),
            enabled = name!="" && description!="" && type!="" && ingredients!="" && check

        ) {
Text("Add Product")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            navController.navigate("productList")
        },modifier=Modifier.fillMaxWidth()) {
          Text("SEE PRODUCT")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Check(){
    val navController = rememberNavController()
    AddProductScreen(navController)
}