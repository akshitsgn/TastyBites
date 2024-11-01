package com.example.ecommerceapp.seller.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerceapp.R


@Composable
fun FoodItemsSection() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(0.dp)
    ) {
        items(listOf("Hotdog Ntap", "Salmon Sushi", "Hotdog Ntap", "Salmon Sushi")) { foodItem ->
            FoodCard(foodItem)
        }
    }
}

@Composable
fun FoodCard(name: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .background(Color.Transparent)
            // .border(1.dp,Color.White,shape= RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, Color.White, shape = RoundedCornerShape(16.dp))
                .background(Color.Black.copy(alpha = 0.9f)),
            contentAlignment = Alignment.TopEnd
        ) {

            Text(
                text = " 3.5 \u2605 ",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                color = Color.White
            )

            Image(
                painter = painterResource(id = R.drawable.food2), // Replace with your background image
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.7f)
            )

        }
        Column (
            modifier= Modifier.border(1.dp, Color.White ,shape = RoundedCornerShape(16.dp))
        ){
            Column(
                modifier = Modifier
                    .border(1.dp, Color.White ,shape = RoundedCornerShape(16.dp))
                    .padding(4.dp)
                    .fillMaxSize()
                    .background(Color.LightGray)
            ) {
                Row {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))


                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Pure Veg ",
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = Color.Black.copy(alpha = 0.2f))
                Text(
                    text = "60 % OFF up to \u20B9 100",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

        }
    }
}