package com.example.huertohogar.ui.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogar.navigation.Screen
import com.example.huertohogar.repository.HuertoHogarViewModel
import com.example.huertohogar.ui.theme.EmeraldGreen
import com.example.huertohogar.ui.theme.SoftWhite

@Composable
fun RegisterScreen(navController: NavController, viewModel: HuertoHogarViewModel) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val passwordsMatch = password == confirmPassword
    val isPasswordLengthValid = password.length >= 6
    val isPasswordValid = isPasswordLengthValid && passwordsMatch

    val isFormValid = remember(firstName, lastName, email, age, password, confirmPassword) {
        firstName.isNotBlank() &&
                lastName.isNotBlank() &&
                email.isNotBlank() &&
                age.isNotBlank() &&
                password.length >= 6 &&
                password == confirmPassword
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Usuario") },
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SoftWhite)
                .padding(it)
                .padding(32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("Nombres") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Apellidos") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("Edad") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), modifier = Modifier.fillMaxWidth())
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                isError = !passwordsMatch && confirmPassword.isNotBlank()
            )

            if (isPasswordValid && confirmPassword.isNotBlank()) {
                Text("Contraseñas válidas", color = EmeraldGreen)
            } else if (!isPasswordLengthValid && password.isNotBlank()) {
                Text("La contraseña debe tener al menos 6 caracteres", color = MaterialTheme.colors.error)
            } else if (!passwordsMatch && confirmPassword.isNotBlank()) {
                Text("Las contraseñas no coinciden", color = MaterialTheme.colors.error)
            }


            Button(
                onClick = {
                    viewModel.register(email, password) {
                        navController.navigate(Screen.Products.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
                },
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = EmeraldGreen)
            ) {
                Text("Registrarse", color = Color.White)
            }
        }
    }
}
