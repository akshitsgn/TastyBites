package com.example.ecommerceapp.common.models

data class Buyer(
    val id: String="",
    val name:String="",
    val email:String="",
    val contact : String="",
    val alternateContact: String="",
    val imageUri:String="",
    val medicalCondition:String="",
    val address: String="",
    val currentStep : Int=1,
    val appFeedback: String=""
)