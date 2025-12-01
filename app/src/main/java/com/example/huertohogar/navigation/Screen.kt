package com.example.huertohogar.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Definición de rutas de navegación
 */
sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Login : Screen("login", "Inicio de Sesión")
    object Register : Screen("register", "Registro de Usuario")
    object Products : Screen("products", "Catálogo", Icons.Filled.Home)
    object Cart : Screen("cart", "Mi Carrito", Icons.Filled.ShoppingCart)

    // Rutas de Perfil
    object Profile : Screen("profile", "Mi Perfil", Icons.Filled.AccountCircle)
    object Orders : Screen("orders", "Mis Pedidos", Icons.AutoMirrored.Filled.ListAlt)
    object Addresses : Screen("addresses", "Mis Direcciones", Icons.Filled.LocationOn)
    object Settings : Screen("settings", "Configuración", Icons.Filled.Settings)

    // Otras rutas
    object About : Screen("about", "Acerca de", Icons.Filled.Help)
}