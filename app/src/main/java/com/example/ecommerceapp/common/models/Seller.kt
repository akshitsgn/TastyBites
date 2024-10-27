package com.example.ecommerceapp.common.models

data class Seller(
    val id : String? ="",
    val emailAddress : String?="",
    val currentStep : Int?=1,
    val specialOfferDescription: String?="",
    val specialOfferImage: String?="",
    val ownerName: String?="",
    val panNumber: Int?=1,
    val FSSAIRegNumber : Int?=1,
    val IFSCNumber: Int?=1,
    val GSTIN: Int?=1,
    val bankAccountNumber: Int?=1,
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