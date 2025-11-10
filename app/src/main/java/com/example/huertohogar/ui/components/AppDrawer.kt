package com.example.huertohogar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.huertohogar.navigation.Screen
import com.example.huertohogar.repository.HuertoHogarViewModel
import com.example.huertohogar.ui.theme.DarkGrey
import com.example.huertohogar.ui.theme.EmeraldGreen
import com.example.huertohogar.ui.theme.MustardYellow

/**
 * Contenido del Drawer (Menú lateral)
 */
@Composable
fun DrawerContent(navController: NavController, viewModel: HuertoHogarViewModel) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    val profileOrLoginRoute = if (isLoggedIn) Screen.Profile.route else Screen.Login.route

    Column(modifier = Modifier.fillMaxHeight()) {
        // Encabezado del Drawer (Estilo HuertoHogar)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(EmeraldGreen, RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                .padding(16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Column {
                Text(
                    "HuertoHogar",
                    style = MaterialTheme.typography.h4.copy(color = Color.White, fontSize = 32.sp)
                )
                Text(
                    "Del Campo al Hogar",
                    style = MaterialTheme.typography.body1.copy(color = MustardYellow)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        DrawerItem(icon = Icons.Default.Person, title = "Mis datos", onClick = { navController.navigate(profileOrLoginRoute) })
        DrawerItem(icon = Icons.Default.Receipt, title = "Mis pedidos", onClick = { navController.navigate(profileOrLoginRoute) })
        DrawerItem(icon = Icons.Default.LocationOn, title = "Mis direcciones", onClick = { navController.navigate(profileOrLoginRoute) })
        DrawerItem(icon = Icons.Default.Settings, title = "Configuración de cuenta", onClick = { navController.navigate(profileOrLoginRoute) })

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        if (isLoggedIn) {
            DrawerItem(icon = Icons.Default.ExitToApp, title = "Cerrar Sesión", onClick = { viewModel.logout() })
        } else {
            DrawerItem(icon = Icons.Default.Login, title = "Iniciar Sesión", onClick = { navController.navigate(Screen.Login.route) })
        }
    }
}

/**
 * Item individual del Drawer
 */
@Composable
fun DrawerItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = EmeraldGreen,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(16.dp))
        Text(title, style = MaterialTheme.typography.body1, color = DarkGrey)
    }
}
