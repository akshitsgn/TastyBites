package com.example.tastybites.customer.onboarding

import android.Manifest
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.tastybites.R
import com.example.tastybites.common.models.Buyer
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun BuyersImageDetailScreen(navController: NavController) {
    val viewModel: AddBuyerViewModel = hiltViewModel()
    val uniqueBuyer by viewModel.uniqueBuyer.collectAsState()
    val name = uniqueBuyer?.name
    val address = uniqueBuyer?.address
    val emailAdress= uniqueBuyer?.email
    var medicalCondition by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val currentStep by remember {
        mutableStateOf(3)
    }
    var contact by remember {
        mutableStateOf("")
    }
    var alternatecontact by remember {
        mutableStateOf("")
    }
    var buyerImage by remember {
        mutableStateOf<Uri?>(null)
    }
    val buyer = Buyer(
        name = name.toString(),
        email = emailAdress.toString(),
        address = address.toString(),
        medicalCondition = medicalCondition,
        imageUri = buyerImage?.toString()?:"",
        alternateContact = alternatecontact,
        contact = contact,
        currentStep = currentStep

    )
    val chooserDialogImage = remember {
        mutableStateOf(false)
    }

    val cameraImageUriBuyer = remember {
        mutableStateOf<Uri?>(null)
    }
    val cameraImageLauncherMenu = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            buyerImage = cameraImageUriBuyer.value // Use this for restaurant image
        }
    }

    val restaurantMenuLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            buyerImage = it // Use this for restaurant menu
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
                cameraImageUriBuyer.value = Uri.fromFile(this)
            })
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                cameraImageLauncherMenu.launch(createImageUriMenu())
            }
        }


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

        if (chooserDialogImage.value) {
            ContentSelectionDialog(onCameraSelected = {
                chooserDialogImage.value = false
                if (navController.context.checkSelfPermission(Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    cameraImageLauncherMenu.launch(createImageUriMenu())
                } else {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }, onGallerySelected = {
                chooserDialogImage.value = false
                restaurantMenuLauncher.launch("image/*")
            })
        }
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

                        Box(modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center) {
                            if (buyerImage != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(buyerImage),
                                    contentDescription = "Selected Image",
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, Color.Gray, CircleShape)
                                )
                            } else {
                                IconButton(
                                    onClick = { chooserDialogImage.value = true },
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape)
                                        .background(Color.Gray.copy(alpha = 0.2f))
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.camera),
                                        contentDescription = "Buyer Photo",
                                        modifier = Modifier.size(80.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Box(modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center) {
                            Text(
                                text = "Your Photo",
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily.Cursive,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                        }
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
                            text = "Your Contact Details",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "You will get updates on the food order on this contact Number",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = contact,
                            onValueChange = {
                                contact=it
                            },
                            label = { Text("Contact*") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        OutlinedTextField(
                            value = alternatecontact,
                            onValueChange = {
                                alternatecontact=it
                            },
                            label = { Text("Alternate Contact*") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        OutlinedTextField(
                            value = medicalCondition,
                            onValueChange = {
                                medicalCondition = it
                            },
                            label = { Text("Medical Condition*") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
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
                                text = "This field is optional",
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
                        viewModel.uploadBuyerImage(buyerImage,buyer, onSuccess = {
                            navController.popBackStack("onBoardingStepperBuyer" , false)
                            Toast.makeText(context,"Added Successfully",Toast.LENGTH_SHORT).show()
                        }, onError = {
                            Toast.makeText(context,"Error Adding",Toast.LENGTH_SHORT).show()
                        })
                    },
                    enabled = buyerImage!=null && contact!="" && alternatecontact!="",
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
