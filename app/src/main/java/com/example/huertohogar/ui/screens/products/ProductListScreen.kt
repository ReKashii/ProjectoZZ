package com.example.huertohogar.ui.screens.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(navController: NavController, viewModel: HuertoHogarViewModel) {
    val products by viewModel.products.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    MainScaffold(navController = navController, screen = Screen.Products, viewModel = viewModel) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {

            // 1. Buscador
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                label = { Text("Buscar productos...") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )

            // 2. Filtros de Categoría (Chips)
            if (categories.isNotEmpty()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    items(categories) { category ->
                        val isSelected = category.name == selectedCategory
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.selectCategory(category.name) },
                            label = { Text(category.name) },
                            leadingIcon = if (isSelected) {
                                { Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(18.dp)) }
                            } else null,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }

            // 3. Lista de Productos
            if (products.isEmpty() && searchQuery.isBlank() && selectedCategory == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (products.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No se encontraron productos.", style = MaterialTheme.typography.titleLarge)
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

@Composable
fun ProductItem(product: Product, onAddToCart: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(product.imageUrl),
                contentDescription = product.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(product.category, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                Text("CLP $${"%,.0f".format(product.price)}", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
            }
            IconButton(onClick = onAddToCart) {
                Icon(Icons.Filled.AddShoppingCart, contentDescription = "Agregar", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}