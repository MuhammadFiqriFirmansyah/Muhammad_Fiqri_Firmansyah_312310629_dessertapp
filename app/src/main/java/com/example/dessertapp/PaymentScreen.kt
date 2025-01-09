package com.example.dessertapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dessertapp.ui.screen.CartManager
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(navController: NavController) {
    var paymentStatus by remember { mutableStateOf(PaymentStatus.PENDING) }
    val total = CartManager.getTotalPrice()

    LaunchedEffect(paymentStatus) {
        if (paymentStatus == PaymentStatus.PENDING) {
            delay(5000) // Simulate payment processing
            paymentStatus = PaymentStatus.SUCCESS
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("QRIS Payment") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.qris_code),
                contentDescription = "QRIS Code",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Total: $${String.format("%.2f", total)}",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = when (paymentStatus) {
                    PaymentStatus.PENDING -> "Scan the QRIS code to pay"
                    PaymentStatus.SUCCESS -> "Payment Successful!"
                },
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (paymentStatus == PaymentStatus.SUCCESS) {
                Button(
                    onClick = {
                        CartManager.clearCart()
                        navController.navigate("menu") {
                            popUpTo("menu") { inclusive = true }
                        }
                    }
                ) {
                    Text("Back to Menu")
                }
            } else {
                CircularProgressIndicator()
            }
        }
    }
}

enum class PaymentStatus {
    PENDING, SUCCESS
}

object CartManager {
    val cartItems = mutableListOf<CartItem>()

    fun addItem(item: CartItem) {
        cartItems.add(item)
    }

    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.price }
    }

    fun clearCart() {
        cartItems.clear()
    }

    data class CartItem(val name: String, val price: Double)
}
