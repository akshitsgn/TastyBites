@file:Suppress("NAME_SHADOWING")

package com.example.ecommerceapp.seller.addseller

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.common.models.Seller
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RestaurantDetailsScreen1(navController: NavController) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val viewModel: AddSellerViewModel = hiltViewModel()
    val context = LocalContext.current
    val currentStep by remember{
        mutableIntStateOf(2)
    }
    val sellerId = currentUser?.uid
    var uniqueSeller by remember { mutableStateOf<Seller?>(null) }
    var ownername by remember {
        mutableStateOf("")
    }
    var restaurantname by remember {
        mutableStateOf("")
    }
    var emailaddress by remember{
        mutableStateOf("")
    }
    var address by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    val seller= Seller(
        ownerName = ownername,
        restaurantName = restaurantname,
        address = address,
        emailAddress = emailaddress,
        description = description,
        currentStep = currentStep
    )
    if (sellerId!=null){
        viewModel.getSellerById(
            sellerId = sellerId,
            onSuccess = { seller ->
                uniqueSeller= seller
            },
            onError = {

            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF6F6F6)) // Light gray background
    ) {
        Row(
            modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())
        ){
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


        Spacer(modifier = Modifier.height(24.dp))

        // Basic Details Section
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Basic Details",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = ownername,
                    onValueChange = { ownername= it },
                    label = { Text("Owner's Full Name*") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = restaurantname,
                    onValueChange = { restaurantname = it},
                    label = { Text("Restaurant Name*") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Owner Contact Details Section
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Owner Contact Details",
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
                        value = emailaddress,
                onValueChange = { emailaddress = it},
                label = { Text("Email address*") },
                modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it},
                    label = { Text("Restaurant description*") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "You will receive a verification mail on this ID",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Restaurant Location Details",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = address,
                    onValueChange = {address = it},
                    label = { Text("Restaurant Location*") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "You will deliver food orders from this location",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                viewModel.addSeller(seller, onError = {
                    Toast.makeText(context,"Error adding the user details", Toast.LENGTH_SHORT).show()
                }, onSuccess = {
                    Toast.makeText(context,"successfully added the user details", Toast.LENGTH_SHORT).show()
                })
            },
            enabled = ownername!= "" && restaurantname!="" && address!=""&& description!="" && emailaddress!="",
            colors = ButtonDefaults.buttonColors(Color(0xFFFF5722)), // Orange color
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

        // Help Footer
        Text(
            text = "If you need any help, check out the FAQs",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun RestaurantDetailsScreen2(navController: NavController) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val viewModel: AddSellerViewModel = hiltViewModel()
    val context = LocalContext.current
    val currentStep by remember{
        mutableIntStateOf(3)
    }
    var fSSAINumber by remember {
        mutableStateOf("")
    }
    var gSTINNumber by remember {
        mutableStateOf("")
    }
    var pANNumber by remember{
        mutableStateOf("")
    }
    var ifsc by remember {
        mutableStateOf("")
    }
    var bankAccountNumber by remember {
        mutableStateOf("")
    }
    val seller= Seller(
        GSTIN = gSTINNumber.toInt(),
        FSSAIRegNumber = fSSAINumber.toInt(),
        panNumber = pANNumber.toInt(),
        IFSCNumber = ifsc.toInt(),
        bankAccountNumber = bankAccountNumber.toInt(),
        currentStep = currentStep
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF6F6F6)) // Light gray background
    ) {
        Row(
            modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())
        ){
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


        Spacer(modifier = Modifier.height(24.dp))

        // Basic Details Section
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Restaurant Documents",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = fSSAINumber,
                    onValueChange = { fSSAINumber= it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    label = { Text("FSSAI Reg Number*") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = gSTINNumber,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    onValueChange = { gSTINNumber = it},
                    label = { Text("GSTIN Number*") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Owner Contact Details Section
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
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
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    onValueChange = { ifsc = it},
                    label = { Text("IFSC Code*") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    value = bankAccountNumber,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    onValueChange = { bankAccountNumber= it},
                    label = { Text("Bank Account Number*") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "You will receive a verification mail on this ID",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Restaurant Location Details",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = pANNumber,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    onValueChange = {pANNumber = it},
                    label = { Text("PAN Number*") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "You will deliver food orders from this location",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                viewModel.addSeller(seller, onError = {
                    Toast.makeText(context,"Error adding the user details", Toast.LENGTH_SHORT).show()
                }, onSuccess = {
                    Toast.makeText(context,"successfully added the user details", Toast.LENGTH_SHORT).show()
                })
            },
            colors = ButtonDefaults.buttonColors(Color(0xFFFF5722)), // Orange color
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

        // Help Footer
        Text(
            text = "If you need any help, check out the FAQs",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun check2(){
    //RestaurantDetailsScreen1()
}