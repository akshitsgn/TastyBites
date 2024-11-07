package com.example.ecommerceapp.customer.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.R
import com.example.ecommerceapp.seller.addedproducts.FoodItemsSection
import com.example.ecommerceapp.seller.dashboard.CategorySection
import com.example.ecommerceapp.seller.dashboard.HeaderSection
import com.example.ecommerceapp.seller.dashboard.PromotionBanner
import com.example.ecommerceapp.seller.dashboard.SearchBar


@Composable
fun BuyerDashboardScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Black.copy(alpha = 0.6f)
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.food), // Replace with your background image
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.9f)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.60f)) // Adjust alpha for desired transparency
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)

        ) {
            // Header Section
            HeaderSection()

            Spacer(modifier = Modifier.height(16.dp))
            SearchBar()
            Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(16.dp))

                // Promotion Banner
                PromotionBanner()

                Spacer(modifier = Modifier.height(16.dp))

                // Category Section
                CategorySection()

                Spacer(modifier = Modifier.height(16.dp))

                // Food Items Section
                FoodItemsSection()
            }
        }
    }
