package com.example.ecommerceapp.seller.onboarding

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.ecommerceapp.R
import com.example.ecommerceapp.common.models.Seller
import com.google.firebase.auth.FirebaseAuth

@Composable
fun OnboardingSellerScreen(navController: NavController){
    val viewModel: AddSellerViewModel = hiltViewModel()
    val uniqueSeller by viewModel.uniqueSeller.collectAsState()
    val currentUser = FirebaseAuth.getInstance().currentUser
    val currentStep = uniqueSeller?.currentStep?: 1
    val initialCurrentStep by remember { mutableIntStateOf(1) }
    val context = LocalContext.current
    val sellerId = currentUser?.uid
    var initialUniqueSeller by remember { mutableStateOf<Seller?>(null) }
    var ownername by remember { mutableStateOf("") }
    var restaurantname by remember { mutableStateOf("") }
    var emailaddress by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val seller = Seller(
        ownerName = ownername,
        restaurantName = restaurantname,
        address = address,
        emailAddress = emailaddress,
        description = description,
        currentStep = currentStep
    )


    val backgroundImage: Painter = painterResource(id = R.drawable.food1) // Replace with your background image
    Box(
        modifier = Modifier.fillMaxSize(),
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
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Make your restaurant delivery-ready in 24hrs!",
                fontFamily = FontFamily.Cursive,
                color = Color.White,
                // textAlign = TextAlign.Center,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Fast-track your growth with Tasty-Bites Where flavor meets opportunity!",
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
                    Text(
                        text = "For an easy form filling process, you can keep the following handy.",
                        color = Color.White,
                        fontFamily= FontFamily.Cursive,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(modifier = Modifier.fillMaxWidth(),
                        color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    val items = listOf(
                        "PAN Number",
                        "GSTIN Number",
                        "Bank Details ",
                        "FSSAI Registration Number",
                        "Your Restaurant Menu"
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
                            Text(text = item, color = Color.White, fontSize = 16.sp, fontFamily= FontFamily.Cursive,)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.addSeller(seller, onError = {
                        Toast.makeText(
                            context,
                            "Error adding the user details",
                            Toast.LENGTH_SHORT
                        ).show()
                    }, onSuccess = { Toast.makeText(context, "Welcome Back", Toast.LENGTH_SHORT).show()

                    })
                        navController.navigate("OnBoardingStepperSeller")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726).copy(alpha = 0.6f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Let's Begin!",
                    color = Color.White,
                    fontFamily= FontFamily.Cursive,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "If you need any help, check out the FAQs",
                color = Color.White,
                fontSize = 12.sp
            )
            Text(
                text = "*Subject to T&C",
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