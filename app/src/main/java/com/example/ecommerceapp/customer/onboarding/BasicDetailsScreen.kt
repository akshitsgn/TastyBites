package com.example.ecommerceapp.customer.onboarding

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerceapp.common.models.Buyer
import com.example.ecommerceapp.common.models.Seller
import com.example.ecommerceapp.common.screens.AddUserViewModel
import com.example.ecommerceapp.seller.onboarding.AddSellerViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun BuyersBasicDetailsScreen(navController: NavController) {
    var currentStep by remember {
        mutableStateOf(2)
    }
    var name by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    var emailaddress by remember {
        mutableStateOf("")
    }
    var address by remember {
        mutableStateOf("")
    }
    val viewModel: AddBuyerViewModel= hiltViewModel()
    val buyer = Buyer(
        name = name,
        email = emailaddress,
        address = address,
        currentStep = currentStep

    )

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())
        ) {
            IconButton(onClick = {
                navController.popBackStack("onBoardingStepperBuyer" , false)
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Your Basic Information",
                fontSize = 26.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            color = Color.Black.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Basic Details",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Full Name*") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = emailaddress,
                            onValueChange = { emailaddress = it },
                            label = { Text("Email Address*") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row {
                            Text(
                                text = "\u002A",
                                fontSize = 18.sp,
                                color = Color.Red.copy(alpha=0.7f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "All fields are necessary ",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            color = Color.Black.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Your Location Details",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "You will be delivered your food to this location",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = address,
                            onValueChange = { address = it },
                            label = { Text("Your Location*") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Row {
                            Text(
                                text = "\u002A",
                                fontSize = 18.sp,
                                color = Color.Red.copy(alpha=0.7f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "All fields are necessary ",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                Button(
                    onClick = {
                            viewModel.addBuyer(buyer, onError = {
                                Toast.makeText(context,"Error Adding",Toast.LENGTH_SHORT).show()
                            } , onSuccess = {
                                Toast.makeText(context,"Added successfully",Toast.LENGTH_SHORT).show()
                                navController.popBackStack("onBoardingStepperBuyer" , false)
                            })
                    },
                    enabled = name.isNotBlank() && emailaddress.isNotBlank() && address.isNotBlank() ,
                    colors = ButtonDefaults.buttonColors(Color(0xFFFF5722)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Add Details",
                        color = Color.White
                    )
                }
            }

            item {
                Text(
                    text = "If you need any help, check out the FAQs",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}