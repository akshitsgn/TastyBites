package com.example.ecommerceapp.common.models

data class Reviews(
    val description : String?="",
    val rating : String?="",
    val ratingCategory : String?="",
    val timestamp: Long = System.currentTimeMillis()
)
