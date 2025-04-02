package com.example.tastybites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.tastybites.common.navgraph.NavScreen
import com.example.tastybites.seller.add_product.AddProductScreen
import com.example.tastybites.seller.added_products.AddedProductsScreen
import com.example.tastybites.ui.theme.TastyBitesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TastyBitesTheme {
                val navController= rememberNavController()
                NavScreen()
            }
        }
    }
}

