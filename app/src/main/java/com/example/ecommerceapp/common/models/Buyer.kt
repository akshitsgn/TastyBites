package com.example.ecommerceapp.common.models

data class Buyer(
    val id: String?="",
    val name:String?="",
    val email:String?="",
    val imageUri:String?="",
    val medicalCondition:String?="",
    val description : String?="",
    val issue: String?="",
    val address: String?="",
    val appFeedback: String?=""
)