package com.example.ecommerceapp.customer.dashboard



import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.ecommerceapp.customer.onboarding.AddBuyerViewModel
import com.example.ecommerceapp.seller.onboarding.AddSellerViewModel

@Composable
fun HeaderSection() {
    val viewModel: AddBuyerViewModel = hiltViewModel()
    val uniqueBuyer by viewModel.uniqueBuyer.collectAsState()
    val buyerImage = uniqueBuyer?.imageUri
    val ownerName = uniqueBuyer?.name
    Log.d("check",buyerImage.toString())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            //.padding(12.dp)
            .padding(WindowInsets.statusBars.asPaddingValues()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Spacer(modifier = Modifier.height(8.dp).padding(16.dp))
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
            model = buyerImage,
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