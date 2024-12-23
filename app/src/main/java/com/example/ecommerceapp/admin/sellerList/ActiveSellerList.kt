package com.example.ecommerceapp.admin.sellerList

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ecommerceapp.R
import com.example.ecommerceapp.common.models.Seller
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ActiveSellerSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        placeholder = { Text("Search Seller") },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        }
    )
}
@Composable
fun ActiveSellerListWithSearch(navController: NavController
) {
    val viewModel: AdminViewModel = hiltViewModel()
    val seller = viewModel.restaurants.collectAsState()
    var isDialogOpenDetails by remember {
        mutableStateOf(false)
    }

    var sellerDetails by remember { mutableStateOf<Seller?>(null) }

    val context = LocalContext.current
    var query by remember { mutableStateOf("") }

    // Get the current date
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val currentDate = dateFormat.format(Date())

    if (isDialogOpenDetails && sellerDetails != null) {
        AlertDialog(
            onDismissRequest = {
                isDialogOpenDetails=false
            },
            text = {
                Text(
                    text = "Click to see the complaints of the Seller?",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )
            },
            confirmButton = {
                Button(
                    onClick = {

                        isDialogOpenDetails = false
                        sellerDetails = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1DA736),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .height(48.dp)
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Complaints")
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 26.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Active Seller List",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = currentDate,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }

        ActiveSellerSearchBar(
            query = query,
            onQueryChange = { query = it }
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(seller.value) { seller ->
                if (seller.verified) {
                    ActiveSellerListItem(seller = seller,
                        onItemClick = {
                            isDialogOpenDetails = true
                            sellerDetails = seller
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun ActiveSellerListItem(
    seller: Seller,
    onItemClick:(Seller)-> Unit,
) {

    val greenColor = Color(0xFF029135)
    val redColor = Color(0xFFDC0404)
    val backgroundColor = Color(0xFFFFFFFF)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .clickable(onClick = {}),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor) // Set the background color here
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if(seller.restaurantImage == null) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with actual image resource
                        contentDescription = "Student Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        colorFilter = ColorFilter.tint(Color.Black)// Circular shape for the image
                    )
                }
                else{
                    AsyncImage(model = seller.restaurantImage,
                        contentDescription = "",
                        contentScale = ContentScale.Crop, // Ensures image fills the circle without distortion
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)

                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier.weight(1f) // Takes up remaining space
                ) {
                    Row {
                        Text(
                            text = "${seller.restaurantName} ", // Replace with student name
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${seller.ownerName}", // Replace with student name
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "Address - ${seller.address}", // Replace with student registration number
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        onItemClick(seller)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue.copy(alpha = 0.5f),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "See Complaints")
                }
            }
        }
    }
}

