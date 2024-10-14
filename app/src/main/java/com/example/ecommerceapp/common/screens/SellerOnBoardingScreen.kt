package com.example.ecommerceapp.common.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.R
import com.example.ecommerceapp.seller.addproduct.AddProductScreen
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AutoScrollingImageCarousel(
    imageList: List<Int>,   // List of image resource IDs
    autoScrollDuration: Long = 3000L  // 3 seconds
) {
    val pagerState = rememberPagerState()

    // Auto-scroll functionality
    LaunchedEffect(key1 = pagerState.currentPage) {
        delay(autoScrollDuration)
        val nextPage = (pagerState.currentPage + 1) % imageList.size
        pagerState.animateScrollToPage(nextPage)
    }

    // HorizontalPager for the carousel
    HorizontalPager(
        count = imageList.size,
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 8.dp
        ) {
            Image(
                painter = painterResource(id = imageList[page]),
                contentDescription = "Carousel Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
@Composable
fun OnboardingScreen() {
    val images = listOf(
        R.drawable.ic_launcher_background,  // Add your image resources here
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background
    )

    AutoScrollingImageCarousel(imageList = images)
}
@Preview(showBackground = true)
@Composable
fun Check1(){
    OnboardingScreen()
}