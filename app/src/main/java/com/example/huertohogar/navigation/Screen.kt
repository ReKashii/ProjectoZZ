package com.example.huertohogar.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Definición de rutas de navegación
 */
sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Login : Screen("login", "Inicio de Sesión")
    object Register : Screen("register", "Registro de Usuario")
    object Products : Screen("products", "Catálogo HuertoHogar", Icons.Filled.Home)
    object Cart : Screen("cart", "Mi Carrito", Icons.Filled.ShoppingCart)
    object Profile : Screen("profile", "Mi Perfil", Icons.Filled.Person)
    object About : Screen("about", "Acerca de", Icons.Filled.Info)
}
