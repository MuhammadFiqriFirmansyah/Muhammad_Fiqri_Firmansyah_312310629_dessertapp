package com.example.dessertapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dessertapp.model.Dessert
import com.example.dessertapp.ui.screen.*
import com.example.dessertapp.ui.theme.DessertAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Sample dessert data
        val desserts = listOf(
            Dessert("1", "Chocolate Cake", 18.99, R.drawable.chocolate_cake),
            Dessert("2", "Strawberry Cheesecake", 20.99, R.drawable.strawberry_cheesecake),
            Dessert("3", "Vanilla Ice Cream", 8.00, R.drawable.vanilla_ice_cream)
        )

        setContent {
            DessertAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "splash") {
                        composable("splash") { SplashScreen(navController) }
                        composable("menu") { MenuScreen(navController, desserts) }
                        composable("detail/{dessertId}") { backStackEntry ->
                            val dessertId = backStackEntry.arguments?.getString("dessertId")
                            val dessert = desserts.find { it.id == dessertId }
                            dessert?.let {
                                DessertDetailScreen(navController, it)
                            }
                        }
                        composable("cart") { CartScreen(navController) }
                        composable("payment") { PaymentScreen(navController) }
                    }
                }
            }
        }
    }
}

