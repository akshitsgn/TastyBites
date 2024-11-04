package com.example.ecommerceapp.common.models

import android.net.Uri

data class Reviews(
    val description : String="",
    val userName:String="",
    val userImage: String="",
    val rating : Int=0,
    val foodComplaint: String="",
    val complaintDescription: String="",
    val timestamp: Long = System.currentTimeMillis()
)
