package com.example.ecommerceapp.seller.dashboard

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerceapp.R
import com.example.ecommerceapp.seller.addseller.AddSellerViewModel
import kotlinx.coroutines.delay
import kotlin.math.round


@Composable
fun SellerDashboardScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Black.copy(alpha = 0.6f)
            )
    ){
        Image(
            painter = painterResource(id = R.drawable.food3), // Replace with your background image
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
                .padding(16.dp)

        ) {
            // Header Section
            HeaderSection()

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            SearchBar()

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

@Composable
fun HeaderSection() {
    val viewModel: AddSellerViewModel = hiltViewModel()
    val uniqueSeller by viewModel.uniqueSeller.collectAsState()
    val restaurantImage = uniqueSeller?.restaurantMenu
    val ownerName = uniqueSeller?.ownerName
    Log.d("check",restaurantImage.toString())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(WindowInsets.statusBars.asPaddingValues()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
           Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Welcome Back!",
                fontFamily = FontFamily.Cursive,
                fontSize = 39.sp,
                color=Color.White.copy(alpha=0.7f),
                // style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = rememberAsyncImagePainter(model=restaurantImage),
            contentDescription = "Selected Image",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.dp, Color.White, CircleShape)
        )
    }
}

@Composable
fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {
            // Handle search input
        },
        placeholder = { Text(text = "Search here...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = Color.Blue,
                contentDescription = "Search"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent), // Background is transparent for outlined text field
        shape = RoundedCornerShape(16.dp), // Customize the corner radius
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color(0xFFF6F6F6),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Gray
        )
    )
}

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
            modifier=Modifier.border(1.dp, Color.White ,shape = RoundedCornerShape(16.dp))
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
