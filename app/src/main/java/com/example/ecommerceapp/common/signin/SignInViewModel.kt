package com.example.ecommerceapp.common.signin

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(): ViewModel() {
   // Replace with your actual Web Client ID
    private  val GOOGLE_SIGN_IN_REQUEST_CODE = 1001
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState
    private var verificationId: String? = null

    // email auth
    fun signIn(email: String, password: String, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        onError()
                    }
                }
        }
    }


    // Authenticate with Firebase using Google account
//    private fun signInWithGoogleAccount(account: GoogleSignInAccount) {
//        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                _authState.value = AuthState.Success(firebaseAuth.currentUser?.uid)
//            } else {
//                _authState.value = AuthState.Error(task.exception?.message)
//            }
//        }
//    }

    //phone number authentication logic
//    fun sendOtp(phoneNumber: String, activity: Activity) {
//        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
//            .setPhoneNumber(phoneNumber)
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setActivity(activity)
//            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                    signInWithPhoneAuthCredential(credential)
//                }
//
//                override fun onVerificationFailed(e: FirebaseException) {
//                    _authState.value = AuthState.Error(e.message)
//                }
//
//                override fun onCodeSent(newVerificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
//                    verificationId = newVerificationId
//                    _authState.value = AuthState.OtpSent
//                }
//            }).build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }
//
//    fun verifyOtp(code: String) {
//        val credential = PhoneAuthProvider.getCredential(verificationId ?: "", code)
//        signInWithPhoneAuthCredential(credential)
//    }
//
//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                _authState.value = AuthState.Success(firebaseAuth.currentUser?.uid)
//            } else {
//                _authState.value = AuthState.Error(task.exception?.message)
//            }
//        }
//    }
}

