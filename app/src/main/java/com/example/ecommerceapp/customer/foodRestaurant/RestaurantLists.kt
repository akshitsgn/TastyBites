package com.example.ecommerceapp.customer.foodRestaurant

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.ecommerceapp.R
import com.example.ecommerceapp.common.models.FoodItems
import com.example.ecommerceapp.common.models.Seller
import com.example.ecommerceapp.seller.addedproducts.FoodCard
import com.example.ecommerceapp.seller.addedproducts.SellerProductsViewModel

@Composable
fun RestaurantList(){
    val viewModel: RestaurantListViewModel= hiltViewModel()
    val food = viewModel.restaurants.collectAsState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(0.dp)
    ) {
        items(food.value) { restaurant ->
            RestaurantCard(restaurant = restaurant)
        }
    }
}

@Composable
fun RestaurantCard(restaurant: Seller) {
    if(restaurant.restaurantImage!="") {
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
                    .size(220.dp)
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
                AsyncImage(
                    model = restaurant.restaurantImage, // Replace with your background image
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.7f)
                )


            }
            Column(
                modifier = Modifier.border(1.dp, Color.White, shape = RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier
                        .border(1.dp, Color.White, shape = RoundedCornerShape(16.dp))
                        .padding(4.dp)
                        .fillMaxSize()
                        .background(Color.LightGray)
                ) {
                    Row {
                        Text(
                            text = restaurant.ownerName.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Serif,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))


                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = restaurant.description.toString(),
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color.Black.copy(alpha = 0.2f))
                    Text(
                        text = restaurant.specialOfferDescription.toString(),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantListScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(), // Background overlay
        contentAlignment = Alignment.Center
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.food3), // Replace with your background image
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.8f) // Adjust alpha for background image transparency
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.55f)) // Adjust alpha for desired transparency
        )

        // Foreground Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f)) // Additional overlay
        ) {
            // Top App Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .padding(WindowInsets.statusBars.asPaddingValues())// Adjust padding as needed
                    .background(color = Color.White.copy(alpha = 0.1f))
                    .clip(RoundedCornerShape(8.dp)) // Clips the Box to match the border shape
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* Handle text change */ },
                    label = { Text("Search Restaurants...", color = Color.White) },
                    textStyle = TextStyle(color = Color.White),
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor =  Color.White.copy(alpha = 0.1f),
                        cursorColor = Color.White,
                        textColor = Color.White,
                        placeholderColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp)) // Ensures the text field respects the rounded corners
                )
            }


            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    listOf(
                        "Mosaic - Crowne Plaza",
                        "Spice Art - Crowne Plaza"
                    )
                ) { restaurantName ->
                    RestaurantCard(restaurantName)
                }
            }
        }
    }
}

@Composable
fun RestaurantCard(restaurantName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.1f)) // Transparent background for the whole column
            .padding(16.dp)
            .clip(RoundedCornerShape(29.dp)) // Optional: to give rounded corners
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.food), // Replace with your image
                contentDescription = "Restaurant Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp)) // Rounded corners on the image
                    .background(Color.White.copy(alpha = 0.2f)), // Slight transparency for image background
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = restaurantName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White // Ensure text is readable on a dark background
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "4.5",
                        fontSize = 14.sp,
                        color = Color(0xFF4CAF50)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "nearbuy.com",
                        color = Color(0xFFF88379).copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.End
                    )
                }
                Text(
                    text = "Rohini Sector 12 - 1.5 km away",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Deals: Breakfast, Lunch & Dinner Buffets Starting at Rs. 999",
            color = Color(0xFF4CAF50)
        )
        Text(
            text = "Events: Lucky Ali live and more, 15 Nov",
            color = Color.White
        )
        Spacer(modifier = Modifier.height(11.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White.copy(alpha = 0.3f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "500+ Bought",
                color = Color(0xFFB2FFFF)
            )
            TextButton(onClick = { /* Add to favorites action */ }) {
                Text("Add to Favourite", color = Color.White)
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = Color.Red.copy(alpha = 0.6f)
                )
            }
        }
    }
    }


@Preview(showBackground = true)
@Composable
fun PreviewRestaurantListScreen() {
    RestaurantListScreen()
}
