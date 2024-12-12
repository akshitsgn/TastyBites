package com.example.ecommerceapp.common.signup

sealed class AuthState {
    object Idle : AuthState()
    object OtpSent : AuthState()
    data class Success(val userId: String?) : AuthState()
    data class Error(val message: String?) : AuthState()
}