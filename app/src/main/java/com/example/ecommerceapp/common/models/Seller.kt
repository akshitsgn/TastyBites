package com.example.ecommerceapp.common.models

data class Seller(
    val id : String? ="",
    val emailAddress : String?="",
    val currentStep : Int?=1,
    val specialOfferDescription: String?="",
    val specialOfferImage: String?="",
    val ownerName: String?="",
    val panNumber: String?="",
    val fssairegNumber: String?="",
    val ifscnumber: String?="",
    val gstin : String?="",
    val bankAccountNumber: String?="",
    val restaurantName: String?="",
    val address: String?="",
    val description: String?="",
    val overallRating: List<Int> ?= emptyList(),
    val complaints: List<String>? = emptyList(),
    val restaurantImage: String?="",
    val foodItems: List<FoodItems> = emptyList(),
    val topDishesImageUri: List<String> ? = emptyList(),
    val restaurantMenu : String? =""
)