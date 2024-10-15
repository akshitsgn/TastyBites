package com.example.ecommerceapp.common.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.common.signin.SignInScreen
import com.example.ecommerceapp.common.signup.SignUpScreen
import com.example.ecommerceapp.seller.addedproducts.ProductsListScreen
import com.example.ecommerceapp.seller.addproduct.AddProductScreen
import com.example.ecommerceapp.seller.addseller.AddSellerScreen

@Composable
fun NavScreen(){
    val navController= rememberNavController()

    NavHost(navController = navController, startDestination = "SignIn") {
        composable("Signup"){
            SignUpScreen(navController)
        }
        composable("SignIn"){
            SignInScreen(navController)
        }
        composable("AddProduct"){
           AddProductScreen(navController)
        }
        composable("productList"){
            ProductsListScreen()
        }
        composable("AddSeller"){
            AddSellerScreen()
        }

    }
}