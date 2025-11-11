package com.example.huertohogar.ui.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogar.navigation.Screen
import com.example.huertohogar.repository.HuertoHogarViewModel
import com.example.huertohogar.ui.components.MainScaffold

@Composable
fun ProfileScreen(navController: NavController, viewModel: HuertoHogarViewModel) {
    MainScaffold(navController = navController, screen = Screen.Profile, viewModel = viewModel) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Se eliminó la lógica de la galería que causaba el crash.
            // Ahora solo se muestra la lista de opciones.

            ProfileItem(Icons.Default.Person, "Mis datos")
            Divider()
            ProfileItem(Icons.Default.Receipt, "Mis pedidos")
            Divider()
            ProfileItem(Icons.Default.LocationOn, "Mis direcciones")
            Divider()
            ProfileItem(Icons.Default.Settings, "Configuración de cuenta")
            Divider()
            ProfileItem(Icons.Default.ExitToApp, "Cerrar sesión", onClick = { viewModel.logout() })
        }
    }
}

@Composable
fun ProfileItem(icon: ImageVector, title: String, onClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(imageVector = icon, contentDescription = title)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, style = MaterialTheme.typography.body1)
    }
}