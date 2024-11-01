@file:Suppress("NAME_SHADOWING")

package com.example.ecommerceapp.seller.addseller

import android.Manifest
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerceapp.common.models.Seller
import com.google.firebase.auth.FirebaseAuth
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RestaurantDetailsScreen1(navController: NavController) {
    // Owner Personal Details
    val currentUser = FirebaseAuth.getInstance().currentUser
    val viewModel: AddSellerViewModel = hiltViewModel()
    val context = LocalContext.current
    val currentStep by remember { mutableIntStateOf(2) }
    val sellerId = currentUser?.uid
    var uniqueSeller by remember { mutableStateOf<Seller?>(null) }
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

    if (sellerId != null) {
        viewModel.getSellerById(
            sellerId = sellerId,
            onSuccess = { seller -> uniqueSeller = seller },
            onError = { /* handle error */ }
        )
    }
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
        ) {

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Basic Details Section
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
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
                            value = ownername,
                            onValueChange = { ownername = it },
                            label = { Text("Owner's Full Name*") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = restaurantname,
                            onValueChange = { restaurantname = it },
                            label = { Text("Restaurant Name*") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Owner Contact Details Section
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
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
                            onValueChange = { emailaddress = it },
                            label = { Text("Email address*") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
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
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Restaurant Location Details Section
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Restaurant Location Details",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = address,
                            onValueChange = { address = it },
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
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Button Section
            item {
                Button(
                    onClick = {
                        viewModel.addSeller(seller, onError = {
                            Toast.makeText(
                                context,
                                "Error adding the user details",
                                Toast.LENGTH_SHORT
                            ).show()
                        }, onSuccess = { Toast.makeText(context, "Successfully added the user details", Toast.LENGTH_SHORT).show()
                            navController.popBackStack("OnBoardingStepper", false)

                        })
                    },
                    enabled = ownername.isNotBlank() && restaurantname.isNotBlank() && address.isNotBlank() && description.isNotBlank() && emailaddress.isNotBlank(),
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
            }

            // Help Footer
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
                    modifier = Modifier.fillMaxWidth(),
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
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Owner Contact Details Section
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
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

                        Text(
                            text = "You will receive order payments in this account",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Owner PAN Details Section
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Owner PAN Details",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = pANNumber,
                            onValueChange = { pANNumber = it },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            label = { Text("PAN Number*") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Provide correct credentials for registering the restaurant",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Add Details Button
            item {
                Button(
                    onClick = {
                        viewModel.updateSellerBankDetails(fSSAINumber,currentStep,ifsc,bankAccountNumber,gSTINNumber,pANNumber, onSuccess = {
                            Toast.makeText(context,"Successfully added",Toast.LENGTH_SHORT).show()
                            navController.popBackStack("OnBoardingStepper", false)

                        }, onError = {
                            Toast.makeText(context,"Error Occurred",Toast.LENGTH_SHORT).show()
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
@Composable
fun RestaurantDetailsScreen3(navController: NavController) {


    // owner Bank details
    val currentUser = FirebaseAuth.getInstance().currentUser
    val viewModel: AddSellerViewModel = hiltViewModel()
    val uniqueSeller by viewModel.uniqueSeller.collectAsState()
    val context = LocalContext.current
    val ownername = uniqueSeller?.ownerName
    val restaurantname = uniqueSeller?.restaurantName
    val emailaddress = uniqueSeller?.emailAddress
    val address = uniqueSeller?.address
    val description = uniqueSeller?.description
    val fSSAINumber = uniqueSeller?.fssairegNumber
    val gSTINNumber = uniqueSeller?.gstin
    val pANNumber = uniqueSeller?.panNumber
    val ifsc  = uniqueSeller?.ifscnumber
    val bankAccountNumber = uniqueSeller?.bankAccountNumber
    val currentStep by remember {
        mutableIntStateOf(4)
    }
    var restaurantMenu by remember {
        mutableStateOf<Uri?>(null)
    }
    var restaurantImage by remember{
        mutableStateOf<Uri?>(null)
    }
    val chooserDialogMenu = remember {
        mutableStateOf(false)
    }
    val chooserDialogRestaurant = remember {
        mutableStateOf(false)
    }

    val cameraImageUriMenu = remember {
        mutableStateOf<Uri?>(null)
    }
    val cameraImageUriRestaurant = remember {
        mutableStateOf<Uri?>(null)
    }

    val cameraImageLauncherMenu = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            restaurantMenu = cameraImageUriMenu.value // Use this for restaurant image
        }
    }
    val cameraImageLauncherRestaurant = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            restaurantImage = cameraImageUriRestaurant.value // Use this for restaurant image
        }
    }

    val restaurantMenuLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            restaurantMenu = it // Use this for restaurant menu
        }
    }
    val restaurantImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            restaurantImage = it // Use this for restaurant menu
        }
    }

    fun createImageUriMenu(): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = ContextCompat.getExternalFilesDirs(
            navController.context, Environment.DIRECTORY_PICTURES
        ).first()
        return FileProvider.getUriForFile(navController.context,
            "${navController.context.packageName}.provider",
            File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
                cameraImageUriMenu.value = Uri.fromFile(this)
            })
    }
    fun createImageUriRestaurant(): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = ContextCompat.getExternalFilesDirs(
            navController.context, Environment.DIRECTORY_PICTURES
        ).first()
        return FileProvider.getUriForFile(navController.context,
            "${navController.context.packageName}.provider",
            File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
                cameraImageUriRestaurant.value = Uri.fromFile(this)
            })
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                cameraImageLauncherMenu.launch(createImageUriMenu())
                cameraImageLauncherRestaurant.launch(createImageUriRestaurant())
            }
        }

    val seller = Seller(
        ownerName = ownername,
        restaurantName = restaurantname,
        emailAddress = emailaddress,
        address = address,
        description = description,
        fssairegNumber = fSSAINumber,
        gstin = gSTINNumber,
        panNumber = pANNumber,
        ifscnumber = ifsc,
        bankAccountNumber = bankAccountNumber,
        currentStep = currentStep,
        restaurantMenu = restaurantMenu?.toString()?:"",
        restaurantImage = restaurantImage?.toString()?:""
    )

    @Composable
    fun ContentSelectionDialog(onCameraSelected: () -> Unit, onGallerySelected: () -> Unit) {
        AlertDialog(onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = onCameraSelected) {
                    Text(text = "Camera")
                }
            },
            dismissButton = {
                TextButton(onClick = onGallerySelected) {
                    Text(text = "Gallery")
                }
            },
            title = { Text(text = "Select your source?") },
            text = { Text(text = "Would you like to pick an image from the gallery or use the") })
    }

    Column(modifier = Modifier.fillMaxSize()) {

        if (chooserDialogMenu.value) {
            ContentSelectionDialog(onCameraSelected = {
                chooserDialogMenu.value = false
                if (navController.context.checkSelfPermission(Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    cameraImageLauncherMenu.launch(createImageUriMenu())
                } else {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }, onGallerySelected = {
                chooserDialogMenu.value = false
                restaurantMenuLauncher.launch("image/*")
            })
        }
        if (chooserDialogRestaurant.value) {
            ContentSelectionDialog(onCameraSelected = {
                chooserDialogRestaurant.value = false
                if (navController.context.checkSelfPermission(Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    cameraImageLauncherRestaurant.launch(createImageUriRestaurant())
                } else {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }, onGallerySelected = {
                chooserDialogRestaurant.value = false
                restaurantImageLauncher.launch("image/*")
            })
        }

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
                text = "Restaurant Details",
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
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Box(modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center) {
                            if (restaurantMenu != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(restaurantMenu),
                                    contentDescription = "Selected Image",
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, Color.Gray, CircleShape)
                                )
                            } else {
                                IconButton(
                                    onClick = { chooserDialogMenu.value = true },
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape)
                                        .background(Color.Gray.copy(alpha = 0.2f))
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add Photo",
                                        tint = Color.DarkGray,
                                        modifier = Modifier.size(60.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                   Box(modifier = Modifier.fillMaxWidth(),
                       contentAlignment = Alignment.Center) {
                       Text(
                           text = "Restaurant Menu ",
                           textAlign = TextAlign.Center,
                           fontFamily = FontFamily.Cursive,
                           fontSize = 28.sp,
                           fontWeight = FontWeight.SemiBold,
                           color = Color.Black
                       )
                       }

                    }
                }
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Box(modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center) {
                            if (restaurantImage != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(restaurantImage),
                                    contentDescription = "Selected Image",
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, Color.Gray, CircleShape)
                                )
                            } else {
                                IconButton(
                                    onClick = { chooserDialogRestaurant.value = true },
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape)
                                        .background(Color.Gray.copy(alpha = 0.2f))
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add Photo",
                                        tint = Color.DarkGray,
                                        modifier = Modifier.size(60.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Box(modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center) {
                            Text(
                                text = "Restaurant Image",
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily.Cursive,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
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
                      viewModel.uploadMenuImageAndAddSeller( restaurantMenu,restaurantImage, seller , onError = {
                          Toast.makeText(context,"Error Occurred", Toast.LENGTH_SHORT).show()
                      } , onSuccess = {
                          Toast.makeText(context,"Added successfully", Toast.LENGTH_SHORT).show()
                          navController.popBackStack("OnBoardingStepper", false)
                      } )
                    },
                    enabled = restaurantMenu!= null,
                    colors = ButtonDefaults.buttonColors(Color(0xFFFF5722)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Add Menu",
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



@Preview(showBackground = true)
@Composable
fun check2(){
    //RestaurantDetailsScreen1()
}