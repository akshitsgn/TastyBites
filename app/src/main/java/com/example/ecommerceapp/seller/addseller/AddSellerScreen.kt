package com.example.ecommerceapp.seller.addseller

import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.R
import com.example.ecommerceapp.common.models.Seller
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun AddSellerScreen(){

    val navController = rememberNavController()
    val viewModel: AddSellerViewModel = hiltViewModel()
    val context = LocalContext.current
    var name by remember {
        mutableStateOf("")
    }
    var address by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var restaurantImage by remember {
        mutableStateOf<Uri?>(null)
    }
    var restaurantMenu by remember {
        mutableStateOf<Uri?>(null)
    }

    val seller= Seller(
            name = name,
            address = address,
            description = description,
            restaurantImage = restaurantImage?.toString()?:"",
            restaurantMenu = restaurantMenu?.toString()?:""
        )

    val chooserDialog = remember {
        mutableStateOf(false)
    }

    val cameraImageUri = remember {
        mutableStateOf<Uri?>(null)
    }

    val cameraImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            restaurantImage = cameraImageUri.value // Use this for restaurant image
        }
    }

    val restaurantImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            restaurantImage = it // Use this for restaurant image
        }
    }

    val restaurantMenuLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            restaurantMenu = it // Use this for restaurant menu
        }
    }

    fun createImageUri(): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = ContextCompat.getExternalFilesDirs(
            navController.context, Environment.DIRECTORY_PICTURES
        ).first()
        return FileProvider.getUriForFile(navController.context,
            "${navController.context.packageName}.provider",
            File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
                cameraImageUri.value = Uri.fromFile(this)
            })
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                cameraImageLauncher.launch(createImageUri())
            }
        }
Column(
modifier = Modifier
.fillMaxSize()
.padding(16.dp),
verticalArrangement = Arrangement.Center

) {
    // Name TextField
    TextField(
        value = name,
        onValueChange = { name = it },
        label = { Text("Seller Name") },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
    // Type TextField
    TextField(
        value = address,
        onValueChange = { address = it },
        label = { Text("Seller Address") },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
    // Description TextField
    TextField(
        value = description,
        onValueChange = { description = it },
        label = { Text("Seller Description") },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
    Button(onClick = {
        viewModel.addSeller(seller, onError = {


        }, onSuccess = {
            name=""
            description=""
            address=""
        })
    },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Add Seller")
    }
}
}


@Preview(showBackground = true)
@Composable
fun Check(){
    AddSellerScreen()
}















