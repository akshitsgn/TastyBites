package com.example.ecommerceapp.seller.onboarding


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.ecommerceapp.R

@Composable
fun FullScreenLottieAnimation() {
    val viewModel: AddSellerViewModel = hiltViewModel()
    val uniqueSeller by viewModel.uniqueSeller.collectAsState()
    val id = uniqueSeller?.id.toString()
    val context = LocalContext.current
    val composition = rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.lottie_animations))
    var showDialog by remember { mutableStateOf(true) }
    val navController = rememberNavController()
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


        Button(
            onClick = {
                viewModel.updateSellerAnimationStatus(status = true , sellerId = id , onSuccess = {
                    navController.navigate("onBoardingSeller")
                },
                    onError = {
                        Toast.makeText(context,"check your internet connection", Toast.LENGTH_SHORT).show()
                    })
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(Color.Black)
        ) {
            Text(
                text = "OK",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}

