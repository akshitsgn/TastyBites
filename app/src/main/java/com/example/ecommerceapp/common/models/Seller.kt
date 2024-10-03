package com.example.ecommerceapp.common.models

data class Seller(
    val id : String? ="",
    val specialOfferDescription: String?="",
    val specialOfferImage: String?="",
    val name: String?="",
    val address: String?="",
    val description: String?="",
    val overallRating: List<Int> ?= emptyList(),
    val complaints: List<String>? = emptyList(),
    val imageUri: String?="",
    val topDishesImageUri: List<String> ? = emptyList(),
    val restaurantMenu : String? =""
)