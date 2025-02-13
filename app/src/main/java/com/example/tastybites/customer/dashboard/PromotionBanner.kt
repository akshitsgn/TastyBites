package com.example.tastybites.customer.dashboard


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tastybites.R
import kotlinx.coroutines.delay

@Composable
fun PromotionBanner() {
    val imageResources = listOf(
        R.drawable.food,
        R.drawable.food3, // Replace with your image resources
    )

    // State to hold the current image index
    var currentImageIndex by remember { mutableIntStateOf(0) }

    // LaunchedEffect to change the image index every few seconds
    LaunchedEffect(Unit) {
        while (true) {
            delay(7000) // Change image every 3 seconds
            currentImageIndex = (currentImageIndex + 1) % imageResources.size
        }
    }

    // Background image to match the food delivery theme
    val backgroundImage: Painter = painterResource(id = imageResources[currentImageIndex])
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(16.dp))
        // .background(color = Color.Blue) change the color here to match the background color
    ) {
        Box(modifier = Modifier

            .background(color = Color.Black.copy(alpha = 1f))) {

            Image(
                painter = backgroundImage, // Replace with your background image
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.6f)
            )

            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Provide Big Deals",
                        textAlign = TextAlign.Center,
                        fontSize = 29.sp,
                        fontFamily = FontFamily.Cursive,
                        // style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "To Attract Buyers",
                        fontSize = 29.sp,
                        fontFamily = FontFamily.Cursive,
                        color = Color.White
                        // style = MaterialTheme.typography.body2,

                    )
                }
            }
        }
    }
}