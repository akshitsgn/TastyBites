package com.example.ecommerceapp.common.signin



import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignInScreen(viewModel: FirebaseAuthViewModel = viewModel(), onSuccess: () -> Unit) {
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsState()

    // Google SignIn Launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val account = task.result
                account?.let { viewModel.handleGoogleSignInResult(it) }
            }
        }
    )

    // UI
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Welcome to Google Sign-In", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val signInClient = GoogleSignIn.getClient(context, viewModel.gso)
                launcher.launch(signInClient.signInIntent)
            }) {
                Text("Sign In with Google")
            }
        }
    }

    // Listen for Success State
    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onSuccess()
        }
    }

    // Display Error Message
    if (authState is AuthState.Error) {
        Text(
            text = (authState as AuthState.Error).message,
            color = MaterialTheme.colorScheme.error
        )
    }
}


@Composable
fun SuccessScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Login Successful!", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Welcome to the App", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "signin") {
        composable("signin") {
            SignInScreen(onSuccess = { navController.navigate("success") })
        }
        composable("success") {
            SuccessScreen()
        }
    }
}