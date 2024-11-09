package com.example.ecommerceapp.admin.sellerList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun SellerDetailsScreen(SellerId:String,navController: NavController){

    val viewModel:AdminViewModel = hiltViewModel()

    LaunchedEffect(SellerId) {
        viewModel.getSellerById(SellerId)
    }

    val seller = viewModel.uniqueSeller.value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(seller?.description.toString())
    }
}