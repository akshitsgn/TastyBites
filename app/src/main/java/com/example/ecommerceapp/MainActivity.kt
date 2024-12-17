@file:OptIn(ExperimentalMaterialApi::class)

package com.example.ecommerceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.common.navgraph.NavScreen
import com.example.ecommerceapp.common.signin.AppNavigation
import com.example.ecommerceapp.common.signin.LoginScreen
import com.example.ecommerceapp.common.signin.SignInScreen
import com.example.ecommerceapp.common.signup.SignUpScreen
import com.example.ecommerceapp.customer.dashboard.BuyerDashboardScreen
import com.example.ecommerceapp.customer.foodRestaurant.RestaurantListScreen
import com.example.ecommerceapp.ui.theme.EcommerceAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            EcommerceAppTheme {
                NavScreen()
            }
        }
    }
}

