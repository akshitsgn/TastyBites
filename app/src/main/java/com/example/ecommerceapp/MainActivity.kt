@file:OptIn(ExperimentalMaterialApi::class)

package com.example.ecommerceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.admin.sellerList.ActiveSellerListWithSearch
import com.example.ecommerceapp.admin.sellerList.SellerListWithSearch
import com.example.ecommerceapp.common.navgraph.NavScreen
import com.example.ecommerceapp.seller.addedproducts.FoodItemsSection
import com.example.ecommerceapp.seller.addproduct.AddProductScreen
import com.example.ecommerceapp.ui.theme.EcommerceAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            EcommerceAppTheme {
            val navController = rememberNavController()
              FoodItemsSection()
            }
        }
    }
}

