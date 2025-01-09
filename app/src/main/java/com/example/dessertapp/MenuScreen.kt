package com.example.dessertapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class Dessert(val id: String, val name: String, val price: Double, val imageRes: Int)

val desserts = listOf(
    Dessert("1", "Chocolate Cake", 18.99, R.drawable.chocolate_cake),
    Dessert("2", "Strawberry Cheesecake", 20.99, R.drawable.strawberry_cheesecake),
    Dessert("3", "Vanilla Ice Cream", 8.00, R.drawable.vanilla_ice_cream),
    // Tambahkan dessert lainnya di sini
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController, desserts: List<com.example.dessertapp.model.Dessert>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dessert Menu") },
                actions = {
                    IconButton(onClick = { navController.navigate("cart") }) {
                        Icon(painter = painterResource(id = R.drawable.ic_cart), contentDescription = "Cart")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            items(com.example.dessertapp.desserts) { dessert ->
                DessertItem(dessert = dessert, navController = navController)
            }
        }
    }
}

@Composable
fun DessertItem(dessert: Dessert, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { navController.navigate("detail/${dessert.id}") },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = dessert.imageRes),
                contentDescription = dessert.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = dessert.name, fontWeight = FontWeight.Bold)
                Text(text = "$${dessert.price}")
            }
        }
    }
}