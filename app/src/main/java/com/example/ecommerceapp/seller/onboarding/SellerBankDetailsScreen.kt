package com.example.ecommerceapp.seller.onboarding

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerceapp.common.models.Seller
import com.google.firebase.auth.FirebaseAuth


@Composable
fun RestaurantDetailsScreen2(navController: NavController) {
    // owner Bank details
    val currentUser = FirebaseAuth.getInstance().currentUser
    val viewModel: AddSellerViewModel = hiltViewModel()
    val context = LocalContext.current
    val currentStep by remember {
        mutableIntStateOf(3)
    }
    var fSSAINumber by remember { mutableStateOf("") }
    var gSTINNumber by remember { mutableStateOf("") }
    var pANNumber by remember { mutableStateOf("") }
    var ifsc by remember { mutableStateOf("") }
    var bankAccountNumber by remember { mutableStateOf("") }

    val seller = Seller(
        gstin = gSTINNumber,
        fssairegNumber = fSSAINumber,
        panNumber = pANNumber,
        ifscnumber = ifsc,
        bankAccountNumber = bankAccountNumber,
        currentStep = currentStep
    )
    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())
        ) {
            IconButton(onClick = {
                navController.popBackStack("OnBoardingStepper", false)
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                text = "Restaurant Information",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color(0xFFF6F6F6)) // Light gray background
        ) {

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Basic Details Section
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth() .border(1.dp, color = Color.Black.copy(alpha = 0.3f), shape = RoundedCornerShape(16.dp)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Restaurant Documents",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = fSSAINumber,
                            onValueChange = { fSSAINumber = it },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            label = { Text("FSSAI Reg Number*") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = gSTINNumber,
                            onValueChange = { gSTINNumber = it },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            label = { Text("GSTIN Number*") },
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

            // Owner Contact Details Section
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth() .border(1.dp, color = Color.Black.copy(alpha = 0.3f), shape = RoundedCornerShape(16.dp)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Owner Bank Details",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "To get updates on payments, customer complaints, order acceptance, etc",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = ifsc,
                            onValueChange = { ifsc = it },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            label = { Text("IFSC Code*") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        OutlinedTextField(
                            value = bankAccountNumber,
                            onValueChange = { bankAccountNumber = it },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            label = { Text("Bank Account Number*") },
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

            // Owner PAN Details Section
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth() .border(1.dp, color = Color.Black.copy(alpha = 0.3f), shape = RoundedCornerShape(16.dp)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Owner PAN Details",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Provide correct credentials for registering the restaurant",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = pANNumber,
                            onValueChange = { pANNumber = it },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            label = { Text("PAN Number*") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))
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

            // Add Details Button
            item {
                Button(
                    onClick = {
                        viewModel.updateSellerBankDetails(fSSAINumber,currentStep,ifsc,bankAccountNumber,gSTINNumber,pANNumber, onSuccess = {
                            Toast.makeText(context,"Successfully added", Toast.LENGTH_SHORT).show()
                            navController.popBackStack("OnBoardingStepper", false)

                        }, onError = {
                            Toast.makeText(context,"Error Occurred", Toast.LENGTH_SHORT).show()
                        })
                    },
                    enabled = fSSAINumber.isNotEmpty() && gSTINNumber.isNotEmpty() && bankAccountNumber.isNotEmpty() && pANNumber.isNotEmpty() && ifsc.isNotEmpty(),
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

            // Help Footer
            item {
                Spacer(modifier = Modifier.height(8.dp))
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