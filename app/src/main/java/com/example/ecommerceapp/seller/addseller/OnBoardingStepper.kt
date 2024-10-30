package com.example.ecommerceapp.seller.addseller

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerceapp.R
import com.example.ecommerceapp.common.models.Seller
import com.google.firebase.auth.FirebaseAuth

@Composable
fun OnboardingScreen(navController: NavController) {
    val viewModel: AddSellerViewModel = hiltViewModel()
    val uniqueSeller by viewModel.uniqueSeller.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

     Image(painter = painterResource(id = R.drawable.food3), contentDescription = "food background",
         modifier = Modifier
             .fillMaxSize()
             .alpha(0.8f),
         contentScale = ContentScale.Crop
         )
        if(uniqueSeller==null){
            Box(modifier = Modifier.fillMaxSize().
            background(color = Color.White.copy(alpha=0.8f))
            ){
                CircularProgressIndicator(modifier=Modifier.
                align(Alignment.Center),
                    color=Color.Black.copy(0.9f)
                )
            }
        }
        

        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(0.7f)))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .padding(WindowInsets.statusBars.asPaddingValues())
                .verticalScroll(rememberScrollState()),
        ) {

            Text(
                text = "Let's Begin",
                fontSize = 50.sp,
                color = Color.White,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "Onboarding You!",
                fontSize = 44.sp,
                color = Color.White,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "In less than 10 minutes",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(40.dp))

            // Stepper
            OnboardingStepper(
                currentStep = uniqueSeller?.currentStep ?: 1,
                steps = listOf(
                    "Restaurant Information" to "Location, Owner details, Open & Close hrs.",
                    "Restaurant Documents" to "Bank details, FSSAI Licence , PAN card.",
                    "Menu Setup" to "Restaurant Menu",
                    "Partner Contract" to "Legal Contract Certificate"
                ),
                navController = navController
            )



        }
    }
}

@Composable
fun OnboardingStepper(
    currentStep: Int,
    navController: NavController,
    steps: List<Pair<String, String>>
) {
    Column() {
        steps.forEachIndexed { index, step ->
            StepItem(
                stepNumber = index + 1,
                title = step.first,
                description = step.second,
                isActive = index + 1 == currentStep,
                isCompleted = index + 1 < currentStep,
                currentStep = currentStep,
                navController = navController
            )
            if (index < steps.size - 1) {

                Divider(
                    color = Color.White.copy(alpha = 0.5f),
                    thickness = 14.dp,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .height(40.dp)
                        .width(1.dp)
                )
            }
        }
    }
}

@Composable
fun StepItem(
    navController: NavController,
    currentStep:Int,
    stepNumber: Int,
    title: String,
    description: String,
    isActive: Boolean,
    isCompleted: Boolean
) {
    val viewModel: AddSellerViewModel = hiltViewModel()
    val uniqueSeller by viewModel.uniqueSeller.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(
                    if (isActive || isCompleted) Color(0xFFFFA726) else Color.White.copy(0.3f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isCompleted) "✓" else stepNumber.toString(),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = title,
                fontSize = if (isActive)23.sp else 19.sp,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                fontFamily = if (isActive) FontFamily.Cursive else FontFamily.Default,
                color = if (isActive) Color.White else Color.White.copy(alpha = 0.5f)
            )
            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            if (isActive) {
                Button(
                    onClick = {
                       if(currentStep==1){
                           navController.navigate("RestaurantDetails1")
                      }
                        else if(currentStep==2){
                           navController.navigate("RestaurantDetails2")
                      }
                        else if(currentStep==3){
                           navController.navigate("RestaurantDetails3")
                      }
                        else{
                          Log.d("4",stepNumber.toString())
                      }

                    },
                    enabled = uniqueSeller!=null,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(text = "Proceed")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    //OnboardingScreen()
}