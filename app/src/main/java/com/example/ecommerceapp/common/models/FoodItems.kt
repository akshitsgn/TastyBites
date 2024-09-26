package com.example.ecommerceapp.common.models

data class FoodItems(
    val id: String="",
    val imageUrl: String?="",
    val name: String="",
    val type: String="",
    val description: String="",
    val ingredients : String="",
    val price: Int = 0,
    val discount: Int = 0,
)