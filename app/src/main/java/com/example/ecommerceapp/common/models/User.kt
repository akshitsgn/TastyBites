package com.example.ecommerceapp.common.models

data class User(
    val id: String="",
    val name:String="",
    val email:String="",
    val imageUri:String?="",
    val medicalCondition:String="",
    val description : String="",
    val issue: String="",
    val address: String="",
    val appFeedback: String=""
)