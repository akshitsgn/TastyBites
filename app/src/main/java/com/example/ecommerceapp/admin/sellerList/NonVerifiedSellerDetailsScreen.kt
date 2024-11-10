package com.example.ecommerceapp.admin.sellerList

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ecommerceapp.R
import com.example.ecommerceapp.seller.onboarding.AddSellerViewModel

@Composable
fun SellerDetailsScreen(SellerId:String,navController: NavController){

    val viewModel:AdminViewModel = hiltViewModel()

    LaunchedEffect(SellerId) {
        viewModel.getSellerById(SellerId)
    }
    val seller = viewModel.uniqueSeller.value
    Column(
        modifier = Modifier
            .fillMaxSize()
           // .verticalScroll(rememberScrollState())
    ){
        AdminSellerScreen(
            id = seller?.id.toString(),
            name = seller?.ownerName.toString(),
            panNo = seller?.panNumber.toString(),
            GSTINNo = seller?.gstin.toString(),
            IFSC = seller?.ifscnumber.toString(),
            FSSAI = seller?.fssairegNumber.toString() ,
            restaurantName = seller?.restaurantName.toString(),
            restaurantImage = seller?.restaurantImage.toString(),
            navController)

    }
}


@Composable
fun AdminSellerScreen(
    id:String,
    name:String,
    panNo : String,
    GSTINNo:String,
    IFSC:String,
    FSSAI : String,
    restaurantName: String,
    restaurantImage: String,
    navController: NavController){
    val context = LocalContext.current
    val viewModel : AdminViewModel = hiltViewModel()
    val backgroundImage: Painter = painterResource(id = R.drawable.food1) // Replace with your background image
    Box(
       // modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = backgroundImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.8f)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.7f))
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(model = restaurantImage,
                contentDescription = "",
                contentScale = ContentScale.Crop, // Ensures image fills the circle without distortion
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)

            )
            Spacer(modifier = Modifier.height(20.dp))
            androidx.compose.material3.Text(
                text = " ${name}",
                fontFamily = FontFamily.Cursive,
                color = Color.White,
                // textAlign = TextAlign.Center,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            androidx.compose.material3.Text(
                text = "Please verify the seller details",
                fontFamily= FontFamily.Cursive,
                //textAlign = TextAlign.Center,
                color = Color(0xFFFFA726),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(56.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Column(
                ) {
                    androidx.compose.material3.Text(
                        text = "Seller Basic Details",
                        color = Color(0xFFFFA726),
                        fontFamily= FontFamily.Cursive,
                        fontSize = 38.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(modifier = Modifier.fillMaxWidth(),
                        color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    val items = listOf(
                        "Name - ${name}",
                        "Restaurant Name - ${restaurantName}",
                    )

                    items.forEach { item ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with a bullet icon
                                contentDescription = null,
                                tint = Color(0xFFFFA726),
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            androidx.compose.material3.Text(text = "${item}", color = Color.White, fontSize = 20.sp, fontFamily= FontFamily.Cursive,)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Column(
                ) {
                    androidx.compose.material3.Text(
                        text = "Seller Bank Details",
                        color = Color(0xFFFFA726),
                        fontFamily= FontFamily.Cursive,
                        fontSize = 38.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(modifier = Modifier.fillMaxWidth(),
                        color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    val items = listOf(
                        "PAN Number - ${panNo}",
                        "GSTIN Number - ${GSTINNo}",
                        "IFSC Number - ${IFSC}",
                        "FSSAI Number - ${FSSAI}",
                    )

                    items.forEach { item ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with a bullet icon
                                contentDescription = null,
                                tint = Color(0xFFFFA726),
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            androidx.compose.material3.Text(text = item, color = Color.White, fontSize = 20.sp, fontFamily= FontFamily.Cursive,)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        viewModel.updateSellerVerifyStatusDetails(verified = true , sellerId = id , onSuccess = {
                            Toast.makeText(context,"seller successfully verified", Toast.LENGTH_SHORT).show()
                            navController.popBackStack("SellerList", false)
                        },
                            onError = {
                                Toast.makeText(context,"check your internet connection", Toast.LENGTH_SHORT).show()
                            })
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726).copy(alpha = 0.6f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    androidx.compose.material3.Text(
                        text = "Verify Seller!",
                        color = Color.White,
                        fontFamily= FontFamily.Cursive,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                androidx.compose.material3.Text(
                    text = "Please click the verify button to verify",
                    color = Color.White,
                    fontSize = 12.sp
                )
                androidx.compose.material3.Text(
                    text = "*only verified sellers can sell their products",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }


@Preview(showBackground = true)
@Composable
fun check1(){
    // OnboardingSellerScreen()
}