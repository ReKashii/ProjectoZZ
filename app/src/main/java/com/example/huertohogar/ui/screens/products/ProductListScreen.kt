package com.example.huertohogar.ui.screens.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.huertohogar.data.model.Product
import com.example.huertohogar.navigation.Screen
import com.example.huertohogar.repository.HuertoHogarViewModel
import com.example.huertohogar.ui.components.MainScaffold
import com.example.huertohogar.ui.theme.DarkGrey
import com.example.huertohogar.ui.theme.EmeraldGreen
import com.example.huertohogar.ui.theme.SoftWhite

@Composable
fun ProductListScreen(navController: NavController, viewModel: HuertoHogarViewModel) {
    val products by viewModel.products.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    MainScaffold(navController = navController, screen = Screen.Products, viewModel = viewModel) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(SoftWhite)
        ) {

            // Campo de Búsqueda (Requerimiento Funcional)
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                label = { Text("Buscar productos...") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = EmeraldGreen,
                    unfocusedBorderColor = EmeraldGreen.copy(alpha = 0.5f),
                    cursorColor = EmeraldGreen,
                    backgroundColor = Color.White
                )
            )

            if (products.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No se encontraron productos.", style = MaterialTheme.typography.h6)
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(products) { product ->
                        ProductItem(product = product, onAddToCart = { viewModel.addToCart(product) })
                    }
                }
            }
        }
    }
}

/**
 * Item individual del producto en la lista.
 */
@Composable
fun ProductItem(product: Product, onAddToCart: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium),
        elevation = 4.dp,
        backgroundColor = Color.White
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            // Placeholder de Imagen (Requerimiento Funcional)
            Image(
                painter = rememberAsyncImagePainter(product.imageUrl),
                contentDescription = product.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    product.name,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    "CLP $${"%,.0f".format(product.price)} / kg",
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold, color = EmeraldGreen)
                )
                Text(
                    product.description.take(40) + "...",
                    style = MaterialTheme.typography.body1.copy(fontSize = 12.sp, color = DarkGrey.copy(alpha = 0.7f))
                )
            }
            Button(
                onClick = onAddToCart,
                colors = ButtonDefaults.buttonColors(backgroundColor = EmeraldGreen),
                shape = MaterialTheme.shapes.small,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(Icons.Filled.AddShoppingCart, contentDescription = "Agregar", modifier = Modifier.size(20.dp), tint = Color.White)
                Spacer(Modifier.width(4.dp))
                Text("Añadir", color = Color.White)
            }
        }
    }
}
