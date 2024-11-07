package com.example.ecommerceapp.customer.dashboard


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategorySection() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Category",
                fontFamily = FontFamily.Cursive,
                fontSize = 28.sp,
                color = Color.White.copy(alpha=0.7f),
                // style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "See more",
                fontFamily = FontFamily.Cursive,
                fontSize = 20.sp,
                //  style = MaterialTheme.typography.body2,
                color = Color.White.copy(alpha = 0.5f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Category Chips
        Row(modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("All", "Burger", "Veg", "Sushi", "Indian", "Italian", "Pizza", "French Fries").forEach { category ->
                Chip(text = category)
            }
        }
    }
}

@Composable
fun Chip(text: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .border(1.dp, Color.White, shape = RoundedCornerShape(22.dp))
            .padding(4.dp)
        //.background(color = Color.Blue) change the color to match with the background
    ) {
        Box(modifier = Modifier.background(color = Color.Blue.copy(alpha=0.2f))) {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color.Black
            )
        }
    }
}
