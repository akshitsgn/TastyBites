@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tastybites.seller.dashboard


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.tastybites.seller.onboarding.AddSellerViewModel

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
                color= Color.White.copy(alpha=0.7f),
                // style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        AsyncImage(
            model = restaurantImage,
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
            //backgroundColor = Color(0xFFF6F6F6),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Gray
        )
    )
}