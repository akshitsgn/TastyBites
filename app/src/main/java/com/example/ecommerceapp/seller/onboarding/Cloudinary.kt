//package com.example.ecommerceapp.seller.onboarding
//
//import android.net.Uri
//import android.widget.Toast
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.lifecycle.viewmodel.compose.viewModel
//import coil.compose.rememberImagePainter
//import com.cloudinary.Cloudinary
//import com.google.firebase.database.FirebaseDatabase
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.tasks.await
//import java.io.File
//import java.io.FileInputStream
//import java.io.FileOutputStream
//
//interface ImageRepository {
//    suspend fun uploadImageToCloudinary(imageUri: Uri, path: String): String
//    suspend fun saveImageUriToFirebase(imageUrl: String, sellerId: String, type: String)
//}
//
//class ImageRepositoryImpl(
//    private val cloudinaryService: CloudinaryService,
//    private val firebaseService: FirebaseService
//) : ImageRepository {
//
//    override suspend fun uploadImageToCloudinary(imageUri: Uri, path: String): String {
//        // Upload the image to Cloudinary under the path (based on seller/admin)
//        return cloudinaryService.uploadImage(imageUri, path)
//    }
//
//    override suspend fun saveImageUriToFirebase(imageUrl: String, sellerId: String, type: String) {
//        // Save to different paths in Firebase based on the type
//        if (type == "seller") {
//            firebaseService.saveImageUrlToSeller(sellerId, imageUrl)
//        } else {
//            firebaseService.saveImageUrlToAdmin(sellerId, imageUrl)
//        }
//    }
//}
//
//class CloudinaryService {
//
//    private val cloudinary = Cloudinary("cloudinary://<API_KEY>:<API_SECRET>@<CLOUD_NAME>")
//
//    suspend fun uploadImage(imageUri: Uri, path: String): String {
//        // Use the passed path to upload the image to Cloudinary
//        val file = File("path_to_temp_file")
//        val inputStream = FileInputStream(File(imageUri.path))  // Convert Uri to InputStream
//        val outputStream = FileOutputStream(file)
//        inputStream.copyTo(outputStream)
//
//        // Upload image to Cloudinary under the specified path (folder)
//        val result = cloudinary.uploader().upload(file, mapOf("folder" to path))
//        return result["secure_url"] as String  // Return the image URL
//    }
//}
//
//
//class FirebaseService {
//
//    private val database = FirebaseDatabase.getInstance()
//
//    suspend fun saveImageUrlToSeller(sellerId: String, imageUrl: String) {
//        // Save the image URL under the seller's path
//        val ref = database.getReference("seller").child(sellerId)
//        ref.child("imageUri").setValue(imageUrl).await()
//    }
//
//    suspend fun saveImageUrlToAdmin(adminId: String, imageUrl: String) {
//        // Save the image URL under the admin's path
//        val ref = database.getReference("admin").child(adminId)
//        ref.child("imageUri").setValue(imageUrl).await()
//    }
//}
//
//
//class UploadImageUseCase(
//    private val repository: ImageRepository
//) {
//    suspend fun execute(imageUri: Uri, sellerId: String, type: String): String {
//        // Upload the image to Cloudinary and get the URL
//        val cloudinaryPath = if (type == "seller") "seller_images" else "admin_images"
//        val imageUrl = repository.uploadImageToCloudinary(imageUri, cloudinaryPath)
//
//        // Save the image URL to Firebase
//        repository.saveImageUriToFirebase(imageUrl, sellerId, type)
//
//        return imageUrl  // Return the URL of the uploaded image
//    }
//}
//
//class ImageUploadViewModel(
//    private val uploadImageUseCase: UploadImageUseCase
//) : ViewModel() {
//
//    private val _uploadResult = MutableLiveData<Result<String>>()
//    val uploadResult: LiveData<Result<String>> = _uploadResult
//
//    fun uploadImage(imageUri: Uri, sellerId: String, type: String) {
//        viewModelScope.launch {
//            try {
//                // Call UseCase to upload image and save URL to Firebase
//                val result = uploadImageUseCase.execute(imageUri, sellerId, type)
//                _uploadResult.value = Result.Success(result)
//            } catch (e: Exception) {
//                _uploadResult.value = Result.Failure(e)
//            }
//        }
//    }
//}
//
//sealed class Result<out T> {
//    data class Success<out T>(val data: T) : Result<T>()
//    data class Failure(val error: Throwable) : Result<Nothing>()
//}
//
//
//@Composable
//fun UploadImageScreen(
//    sellerId: String,
//    userType: String, // "seller" or "admin"
//
//) {
//     val viewModel: ImageUploadViewModel = viewModel()
//    var imageUri by remember { mutableStateOf<Uri?>(null) }
//    val context = LocalContext.current
//    val uploadResult by viewModel.uploadResult.observeAsState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // Button to pick an image
//        ImagePicker(onImagePicked = { uri ->
//            imageUri = uri
//        })
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Button to upload the image
//        Button(
//            onClick = {
//                imageUri?.let {
//                    viewModel.uploadImage(it, sellerId, userType)  // Pass the type (seller or admin)
//                }
//            }
//        ) {
//            Text("Upload Image")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Observe the result of the upload and show a Toast
//        uploadResult?.let { result ->
//            when (result) {
//                is Result.Success -> {
//                    Toast.makeText(context, "Upload Successful: ${result.data}", Toast.LENGTH_SHORT).show()
//                }
//                is Result.Failure -> {
//                    Toast.makeText(context, "Upload Failed: ${result.error.message}", Toast.LENGTH_SHORT).show()
//                }
//
//                else -> {
//
//                }
//            }
//        }
//    }
//}
//
//
//
//
//@Composable
//fun ImagePicker(onImagePicked: (Uri) -> Unit) {
//    val context = LocalContext.current
//    val imageUri = remember { mutableStateOf<Uri?>(null) }
//
//    // Launch the activity result contract to pick an image
//    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        uri?.let {
//            imageUri.value = it
//            onImagePicked(it) // Call the callback with the picked URI
//        }
//    }
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.fillMaxSize().padding(16.dp)
//    ) {
//        Button(onClick = { launcher.launch("image/*") }) {
//            Text("Pick an image")
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        imageUri.value?.let {
//            val painter: Painter = rememberImagePainter(it)
//            Image(painter = painter, contentDescription = null, modifier = Modifier.size(150.dp))
//        }
//    }
//}
