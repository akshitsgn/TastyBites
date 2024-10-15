package com.example.ecommerceapp.seller.addedproducts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerceapp.common.models.FoodItems
import com.example.ecommerceapp.common.models.Seller

@Composable
fun ProductsListItem(
    foodItems: FoodItems
){
    Card(
    ){
        Column(){
            Text("${foodItems.name}")
            Text("${foodItems.description}")
        }
    }
}

@Composable
fun ProductsListScreen(){
    val viewModel:SellerProductsViewModel= hiltViewModel()
    val products by viewModel.productList.observeAsState(emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),  // Add padding as needed
        contentAlignment = Alignment.Center  // Centers the LazyColumn in both X and Y axis
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()  // Fills the remaining space inside the centered Box
        ) {
            items(products) { product ->
                ProductsListItem(foodItems = product)
            }
        }
    }
}
