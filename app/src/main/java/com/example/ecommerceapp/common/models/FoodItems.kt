package com.example.ecommerceapp.common.models

data class FoodItems(
    val productId: String ="",
    val rating : List<Int> = emptyList(),
    val imageUrl: String="",
    val name: String="",
    val type: String="",
    val description: String="",
    val liked: Boolean=false,
    val ingredients : String="",
    val price: String = "",
    val discount: String = "",
)