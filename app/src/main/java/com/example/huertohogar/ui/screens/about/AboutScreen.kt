package com.example.huertohogar.ui.screens.about

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogar.repository.HuertoHogarViewModel
import com.example.huertohogar.ui.theme.EmeraldGreen

@Composable
fun AboutScreen(navController: NavController, viewModel: HuertoHogarViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Acerca de") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                backgroundColor = EmeraldGreen,
                contentColor = Color.White,
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Acerca de HuertoHogar.\nNuestra misión es llevar la frescura del campo a tu hogar. ¡Promovemos la sostenibilidad! (Contenido Educativo)",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
