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
import com.example.ecommerceapp.seller.addseller.OnboardingScreen
import com.example.ecommerceapp.seller.addseller.OnboardingSellerScreen
import com.example.ecommerceapp.seller.addseller.RestaurantDetailsScreen1
import com.example.ecommerceapp.seller.addseller.RestaurantDetailsScreen2
import com.example.ecommerceapp.seller.addseller.RestaurantDetailsScreen3
import com.example.ecommerceapp.seller.dashboard.SellerDashboardScreen

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
        composable("onBoardingSeller"){
            OnboardingSellerScreen(navController)
        }
        composable("OnBoardingStepper"){
            OnboardingScreen(navController)
        }
        composable("RestaurantDetails1"){
            RestaurantDetailsScreen1(navController)
        }
        composable("RestaurantDetails2"){
            RestaurantDetailsScreen2(navController)
        }
        composable("RestaurantDetails3"){
            RestaurantDetailsScreen3(navController)
        }
        composable("sellerDashboard"){
            SellerDashboardScreen()
        }

    }
}