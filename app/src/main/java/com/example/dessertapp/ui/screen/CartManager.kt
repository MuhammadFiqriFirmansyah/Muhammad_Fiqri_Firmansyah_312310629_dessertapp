package com.example.dessertapp.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.dessertapp.model.CartItem
import com.example.dessertapp.model.Dessert

object CartManager {
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: SnapshotStateList<CartItem> = _cartItems

    fun addToCart(dessert: Dessert, quantity: Int = 1) {
        val existingItem = _cartItems.find { it.dessert.id == dessert.id }
        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            _cartItems.add(CartItem(dessert, quantity))
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        _cartItems.remove(cartItem)
    }

    fun clearCart() {
        _cartItems.clear()
    }

    fun getTotalPrice(): Double {
        return _cartItems.sumOf { it.dessert.price * it.quantity }
    }
}

