package com.example.ecommerceapp.seller.onboarding

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.airbnb.lottie.compose.LottieAnimation
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.ecommerceapp.R
import kotlinx.coroutines.delay


@Composable

fun SellerVerified(navController: NavController){

    val viewModel: AddSellerViewModel = hiltViewModel()
    val uniqueSeller by viewModel.uniqueSeller.collectAsState()
    val verified = uniqueSeller?.verified?: false
    val id = uniqueSeller?.id.toString()
    val context = LocalContext.current
    val composition = rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.lottie_animations))


              Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF4CAF50).copy(alpha = 0.5f)) // Background color (e.g., green)
                ) {

               LottieAnimation(
                   modifier = Modifier.fillMaxSize(),
                    composition = composition.value,
                    iterations = LottieConstants.IterateForever,
                    alignment = Alignment.Center

                )
                  LaunchedEffect(key1 = id) {
                      delay(4000) // Delay for 4 seconds

                          viewModel.updateSellerAnimationStatus(status = true,
                              sellerId = id,
                              onSuccess = {
                                  navController.navigate("onBoardingSeller")
                              },
                              onError = {
                                  Toast.makeText(
                                      context,
                                      "check your internet connection",
                                      Toast.LENGTH_SHORT
                                  ).show()
                              })
                  }


            }
}