package com.example.ecommerceapp.common.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.common.signin.SignInScreen
import com.example.ecommerceapp.common.signup.SignUpScreen
import com.example.ecommerceapp.customer.onboarding.OnboardingBuyersScreen
import com.example.ecommerceapp.seller.addproduct.AddProductScreen
import com.example.ecommerceapp.seller.onboarding.OnboardingScreen
import com.example.ecommerceapp.seller.onboarding.OnboardingSellerScreen
import com.example.ecommerceapp.seller.onboarding.RestaurantDetailsScreen1
import com.example.ecommerceapp.seller.onboarding.RestaurantDetailsScreen2
import com.example.ecommerceapp.seller.onboarding.RestaurantDetailsScreen3
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
        composable("onBoardingSeller"){
            OnboardingSellerScreen(navController)
        }
        composable("onBoardingStepperBuyer"){
            OnboardingBuyersScreen(navController)
        }
        composable("OnBoardingStepperSeller"){
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