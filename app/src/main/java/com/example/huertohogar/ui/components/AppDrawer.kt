package com.example.huertohogar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.huertohogar.navigation.Screen
import com.example.huertohogar.repository.HuertoHogarViewModel

/**
 * Contenido del Drawer (Menú lateral) - Migrado a M3
 */
@Composable
fun DrawerContent(navController: NavController, viewModel: HuertoHogarViewModel, closeDrawer: () -> Unit) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    // Define las rutas correctas
    val profileRoute = if (isLoggedIn) Screen.Profile.route else Screen.Login.route
    val ordersRoute = if (isLoggedIn) Screen.Orders.route else Screen.Login.route
    val addressesRoute = if (isLoggedIn) Screen.Addresses.route else Screen.Login.route
    val settingsRoute = if (isLoggedIn) Screen.Settings.route else Screen.Login.route

    ModalDrawerSheet {
        // Encabezado del Drawer (Estilo M3)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Column {
                Text(
                    "HuertoHogar",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    "Del Campo al Hogar",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 18.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Items de Navegación Corregidos (Goal 3) ---
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Mis datos") },
            label = { Text("Mis datos") },
            selected = false,
            onClick = {
                navController.navigate(profileRoute)
                closeDrawer()
            },
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        NavigationDrawerItem(
            icon = { Icon(Icons.AutoMirrored.Filled.ListAlt, contentDescription = "Mis pedidos") },
            label = { Text("Mis pedidos") },
            selected = false,
            onClick = {
                navController.navigate(ordersRoute) // CORREGIDO
                closeDrawer()
            },
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.LocationOn, contentDescription = "Mis direcciones") },
            label = { Text("Mis direcciones") },
            selected = false,
            onClick = {
                navController.navigate(addressesRoute) // CORREGIDO
                closeDrawer()
            },
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Configuración") },
            label = { Text("Configuración de cuenta") },
            selected = false,
            onClick = {
                navController.navigate(settingsRoute) // CORREGIDO
                closeDrawer()
            },
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Divider(modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp))

        if (isLoggedIn) {
            NavigationDrawerItem(
                icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Cerrar Sesión") },
                label = { Text("Cerrar Sesión") },
                selected = false,
                onClick = {
                    viewModel.logout()
                    closeDrawer()
                },
                modifier = Modifier.padding(horizontal = 12.dp),
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedIconColor = MaterialTheme.colorScheme.error,
                    unselectedTextColor = MaterialTheme.colorScheme.error
                )
            )
        } else {
            NavigationDrawerItem(
                icon = { Icon(Icons.Default.Login, contentDescription = "Iniciar Sesión") },
                label = { Text("Iniciar Sesión") },
                selected = false,
                onClick = {
                    navController.navigate(Screen.Login.route)
                    closeDrawer()
                },
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}