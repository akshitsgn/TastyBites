package com.example.ecommerceapp.common.models

data class Seller(
    val id : String="",
    val name: String="",
    val address: String="",
    val description: String="",
    val rating: List<Int> = emptyList(),
    val complaints: List<String> = emptyList(),
    val imageUri: String?=""
)