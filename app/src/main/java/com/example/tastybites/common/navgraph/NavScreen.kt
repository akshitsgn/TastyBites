package com.example.tastybites.common.navgraph


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tastybites.admin.seller_list.SellerDetailsScreen
import com.example.tastybites.admin.seller_list.SellerListWithSearch
import com.example.tastybites.common.signin.SignInScreen
import com.example.tastybites.common.signup.SignUpScreen
import com.example.tastybites.customer.onboarding.BuyerTermsAndConditions
import com.example.tastybites.customer.onboarding.BuyersBasicDetailsScreen
import com.example.tastybites.customer.onboarding.BuyersImageDetailScreen
import com.example.tastybites.customer.onboarding.OnBoardingBuyersStepperScreen
import com.example.tastybites.customer.onboarding.OnboardingBuyersScreen
import com.example.tastybites.seller.add_product.AddProductScreen
import com.example.tastybites.seller.added_products.AddProductDetailScreen
import com.example.tastybites.seller.added_products.AddedProductsScreen
import com.example.tastybites.seller.dashboard.SellerDashboardScreen
import com.example.tastybites.seller.onboarding.EmailScreen
import com.example.tastybites.seller.onboarding.LegalContractScreen
import com.example.tastybites.seller.onboarding.OnboardingSellerScreen
import com.example.tastybites.seller.onboarding.OnboardingSellerStepperScreen
import com.example.tastybites.seller.onboarding.RestaurantDetailsScreen1
import com.example.tastybites.seller.onboarding.RestaurantDetailsScreen2
import com.example.tastybites.seller.onboarding.RestaurantDetailsScreen3
//import com.example.tastybites.seller.onboarding.SellerVerified


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
        composable("email"){
            EmailScreen(navController)
        }
        composable("AddProduct"){
            AddProductScreen(navController)
        }
        composable("onBoardingSeller"){
            OnboardingSellerScreen(navController)
        }
        composable("onBoardingStepperBuyer"){
            OnBoardingBuyersStepperScreen(navController )
        }
        composable("OnBoardingStepperSeller"){
            OnboardingSellerStepperScreen(navController)
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
        composable("basicBuyerDetails"){
            BuyersBasicDetailsScreen(navController)
        }
        composable("buyerImageScreen"){
            BuyersImageDetailScreen(navController)
        }
        composable("buyerTerms&Conditions"){
            BuyerTermsAndConditions(navController)
        }
        composable("onboardingBuyer"){
            OnboardingBuyersScreen(navController)
        }
        composable("SellerList"){
            SellerListWithSearch(navController)
        }
        composable("legalContract"){
            LegalContractScreen(navController)
        }
        composable("AddedProduct"){
            AddedProductsScreen(navController)
        }
        composable(
            route = "productdetailscreen/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            AddProductDetailScreen(productId ?: "",navController)
        }
        composable(
            route="adminSellerList/{SellerId}",
            arguments = listOf(navArgument("SellerId"){type= NavType.StringType})
        ){ backStackEntry->
            val sellerId = backStackEntry.arguments?.getString("SellerId")
            SellerDetailsScreen(sellerId?:"",navController)
        }

    }
}