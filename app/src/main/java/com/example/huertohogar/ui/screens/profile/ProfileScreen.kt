package com.example.huertohogar.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.huertohogar.navigation.Screen
import com.example.huertohogar.repository.HuertoHogarViewModel
import com.example.huertohogar.ui.components.MainScaffold
import androidx.compose.foundation.clickable
@Composable
fun ProfileScreen(navController: NavController, viewModel: HuertoHogarViewModel) {
    MainScaffold(navController = navController, screen = Screen.Profile, viewModel = viewModel) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Se eliminó la lógica de la galería que causaba el crash.
            // Ahora se muestra una lista de opciones M3 funcional.

            ProfileItem(
                icon = Icons.Default.AccountCircle,
                title = "Mis datos",
                onClick = { /* Navegar a una futura pantalla de "Editar Datos" */ }
            )
            Divider()
            ProfileItem(
                icon = Icons.AutoMirrored.Filled.ListAlt,
                title = "Mis pedidos",
                onClick = { navController.navigate(Screen.Orders.route) } // CORREGIDO
            )
            Divider()
            ProfileItem(
                icon = Icons.Default.LocationOn,
                title = "Mis direcciones",
                onClick = { navController.navigate(Screen.Addresses.route) } // CORREGIDO
            )
            Divider()
            ProfileItem(
                icon = Icons.Default.Settings,
                title = "Configuración de cuenta",
                onClick = { navController.navigate(Screen.Settings.route) } // CORREGIDO
            )
            Divider()
            ProfileItem(
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                title = "Cerrar sesión",
                onClick = { viewModel.logout() },
                isLogout = true
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileItem(icon: ImageVector, title: String, onClick: () -> Unit, isLogout: Boolean = false) {

    val colors = if (isLogout) {
        ListItemDefaults.colors(
            headlineColor = MaterialTheme.colorScheme.error,
            leadingIconColor = MaterialTheme.colorScheme.error
        )
    } else {
        ListItemDefaults.colors()
    }

    ListItem(
        headlineContent = { Text(text = title) },
        leadingContent = { Icon(imageVector = icon, contentDescription = title) },
        modifier = Modifier.clickable(onClick = onClick),
        colors = colors
    )
}