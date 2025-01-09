package com.example.dessertapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dessertapp.ui.screen.CartManager
import com.example.dessertapp.model.CartItem  // Make sure this import is present

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController) {
    val cartItems by remember { mutableStateOf(CartManager.cartItems) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shopping Cart") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel), contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (cartItems.isEmpty()) {
                Text("Your cart is empty", style = MaterialTheme.typography.bodyLarge)
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(cartItems) { cartItem ->
                        CartItemRow(cartItem)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                val total = CartManager.getTotalPrice()
                Text(
                    text = "Total: $${String.format("%.2f", total)}",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigate("payment") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Proceed to Payment")
                }
            }
        }
    }
}

@Composable
fun CartItemRow(cartItem: CartItem) {  // Changed from CartManager.CartItem to CartItem
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(cartItem.dessert.name, modifier = Modifier.weight(1f))
        Text("x${cartItem.quantity}", modifier = Modifier.padding(horizontal = 8.dp))
        Text("$${String.format("%.2f", cartItem.dessert.price * cartItem.quantity)}")
    }
}

