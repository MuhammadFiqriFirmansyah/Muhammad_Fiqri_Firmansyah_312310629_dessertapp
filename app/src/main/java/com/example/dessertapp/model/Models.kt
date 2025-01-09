package com.example.dessertapp.model

data class Dessert(
    val id: String,
    val name: String,
    val price: Double,
    val imageRes: Int
)

data class CartItem(
    val dessert: Dessert,
    var quantity: Int
)
