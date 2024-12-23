package com.example.ecommerceapp.seller.onboarding

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.LottieDrawable
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.ecommerceapp.R

@Composable
fun FullScreenLottieAnimation() {
    // Remember the Lottie animation composition from a raw resource file
    val composition = rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.lottie_animations))

    // Display the Lottie animation in full-screen mode with infinite looping
    LottieAnimation(
        modifier = Modifier.fillMaxSize(),  // Ensures the animation fills the screen
        composition = composition.value,
        iterations = LottieConstants.IterateForever,  // Makes the animation loop infinitely
        alignment = Alignment.Center,  // Centers the animation
    )
}