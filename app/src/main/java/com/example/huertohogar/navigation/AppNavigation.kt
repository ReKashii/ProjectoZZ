package com.example.huertohogar.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.huertohogar.repository.HuertoHogarViewModel
import com.example.huertohogar.repository.ViewModelFactory
import com.example.huertohogar.ui.screens.about.AboutScreen
import com.example.huertohogar.ui.screens.addresses.AddressesScreen
import com.example.huertohogar.ui.screens.cart.CartScreen
import com.example.huertohogar.ui.screens.login.LoginScreen
import com.example.huertohogar.ui.screens.orders.OrdersScreen
import com.example.huertohogar.ui.screens.register.RegisterScreen
import com.example.huertohogar.ui.screens.products.ProductListScreen
import com.example.huertohogar.ui.screens.profile.ProfileScreen
import com.example.huertohogar.ui.screens.settings.SettingsScreen

/**
 * Componente principal de navegación de la aplicación (NavHost).
 */
@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val application = context.applicationContext as Application
    val viewModel: HuertoHogarViewModel = viewModel(factory = ViewModelFactory(application))
    val navController = rememberNavController()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    // La pantalla de inicio siempre será el catálogo de productos
    val startDestination = Screen.Products.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) {
            LoginScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.Products.route) {
            ProductListScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.Cart.route) {
            CartScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.Profile.route) {
            // Proteger la ruta del perfil
            if (isLoggedIn) {
                ProfileScreen(navController = navController, viewModel = viewModel)
            } else {
                // Si no ha iniciado sesión, navegar a Login
                LaunchedEffect(Unit) { navController.navigate(Screen.Login.route) }
            }
        }
        composable(Screen.About.route) {
            AboutScreen(navController = navController, viewModel = viewModel)
        }
        // --- NUEVAS RUTAS AÑADIDAS ---
        composable(Screen.Orders.route) {
            if (isLoggedIn) {
                OrdersScreen(navController = navController)
            } else {
                LaunchedEffect(Unit) { navController.navigate(Screen.Login.route) }
            }
        }
        composable(Screen.Addresses.route) {
            if (isLoggedIn) {
                AddressesScreen(navController = navController)
            } else {
                LaunchedEffect(Unit) { navController.navigate(Screen.Login.route) }
            }
        }
        composable(Screen.Settings.route) {
            if (isLoggedIn) {
                SettingsScreen(navController = navController)
            } else {
                LaunchedEffect(Unit) { navController.navigate(Screen.Login.route) }
            }
        }
    }
}