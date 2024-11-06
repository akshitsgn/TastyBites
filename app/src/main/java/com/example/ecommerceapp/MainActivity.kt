package com.example.ecommerceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.common.navgraph.NavScreen
import com.example.ecommerceapp.common.signup.SignUpScreen
import com.example.ecommerceapp.customer.Feedback.AddUserFeedback
import com.example.ecommerceapp.customer.onboarding.BuyersBasicDetailsScreen
import com.example.ecommerceapp.customer.onboarding.BuyersImageDetailScreen
import com.example.ecommerceapp.customer.onboarding.OnBoardingBuyersStepperScreen
import com.example.ecommerceapp.customer.onboarding.OnboardingBuyersScreen
import com.example.ecommerceapp.seller.addproduct.AddProductScreen

import com.example.ecommerceapp.seller.dashboard.SellerDashboardScreen
import com.example.ecommerceapp.seller.reviews.CustomerFeedbackScreen
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
               // BuyersImageDetailScreen(navController)
               // OnBoardingBuyersStepperScreen(navController)
               // BuyersBasicDetailsScreen(navController)
                NavScreen()
              //  OnboardingBuyersScreen(navController = navController)
               // SignUpScreen(navController = navController)
                // CustomerFeedbackScreen()
               // SellerDashboardScreen()
               // SignUpScreen(navController = navController)
               //AddProductScreen(navController = navController)
                // RatingScreen()
              //  AddUserFeedback(navController = navController)
              //  CustomerFeedbackScreen()
               // AddSellerScreen()
            }
        }
    }
}
