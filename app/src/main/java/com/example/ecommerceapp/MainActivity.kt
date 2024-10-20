package com.example.ecommerceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.common.navgraph.NavScreen
import com.example.ecommerceapp.common.signin.SignInScreen
import com.example.ecommerceapp.common.signup.SignUpScreen
import com.example.ecommerceapp.customer.ratingseller.RatingScreen
import com.example.ecommerceapp.seller.addseller.AddSellerScreen
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

                NavScreen()

            }
        }
    }
}
