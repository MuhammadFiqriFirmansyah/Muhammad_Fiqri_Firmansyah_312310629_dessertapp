package com.example.dessertapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dessertapp.model.Dessert

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DessertDetailScreen(navController: NavController, dessert: Dessert) {
    var quantity by remember { mutableStateOf(1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(dessert.name) },
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
            Image(
                painter = painterResource(id = dessert.imageRes),
                contentDescription = dessert.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = dessert.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium)
            Text(text = "$${String.format("%.2f", dessert.price)}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { if (quantity > 1) quantity-- }) {
                    Text("-")
                }
                Text(
                    text = quantity.toString(),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Button(onClick = { quantity++ }) {
                    Text("+")
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        CartManager.addToCart(dessert, quantity)
                        navController.navigate("cart")
                    }
                ) {
                    Text("Add to Cart")
                }
            }
        }
    }
}



