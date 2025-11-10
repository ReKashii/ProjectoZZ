package com.example.huertohogar.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.huertohogar.navigation.Screen
import com.example.huertohogar.repository.HuertoHogarViewModel
import com.example.huertohogar.ui.theme.DarkGrey
import com.example.huertohogar.ui.theme.EmeraldGreen
import com.example.huertohogar.ui.theme.SoftWhite

@Composable
fun LoginScreen(navController: NavController, viewModel: HuertoHogarViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isLoginEnabled = email.isNotBlank() && password.length >= 6

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inicio de Sesión") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Products.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                backgroundColor = EmeraldGreen,
                contentColor = Color.White,
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SoftWhite)
                .padding(it)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(MaterialTheme.shapes.large),
                elevation = 8.dp,
                backgroundColor = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "HUERTOHOGAR",
                        style = MaterialTheme.typography.h4,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "Inicia sesión para llevar la frescura del campo a tu hogar",
                        style = MaterialTheme.typography.body1.copy(color = DarkGrey.copy(alpha = 0.7f)),
                        textAlign = TextAlign.Center
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo Electrónico") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = EmeraldGreen,
                            unfocusedBorderColor = EmeraldGreen.copy(alpha = 0.5f),
                            cursorColor = EmeraldGreen
                        )
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = EmeraldGreen,
                            unfocusedBorderColor = EmeraldGreen.copy(alpha = 0.5f),
                            cursorColor = EmeraldGreen
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            viewModel.login(email, password) {
                                navController.navigate(Screen.Products.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                }
                            }
                        },
                        enabled = isLoginEnabled,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = EmeraldGreen),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text("Ingresar", color = Color.White, style = MaterialTheme.typography.button.copy(fontSize = 18.sp))
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Si no tienes una cuenta, ", style = MaterialTheme.typography.body1.copy(fontSize = 14.sp))
                        Text(
                            text = "Regístrate aquí",
                            modifier = Modifier.clickable { navController.navigate(Screen.Register.route) },
                            color = EmeraldGreen,
                            style = MaterialTheme.typography.body1.copy(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}
