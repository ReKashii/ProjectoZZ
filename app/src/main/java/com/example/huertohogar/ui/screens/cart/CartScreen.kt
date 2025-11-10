package com.example.huertohogar.ui.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.huertohogar.data.model.CartItem
import com.example.huertohogar.navigation.Screen
import com.example.huertohogar.repository.HuertoHogarViewModel
import com.example.huertohogar.ui.components.MainScaffold
import com.example.huertohogar.ui.theme.DarkGrey
import com.example.huertohogar.ui.theme.EmeraldGreen
import com.example.huertohogar.ui.theme.MustardYellow
import com.example.huertohogar.ui.theme.SoftWhite

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartScreen(navController: NavController, viewModel: HuertoHogarViewModel) {
    val cartItems by viewModel.cart.collectAsState()
    val cartTotal by viewModel.cartTotal.collectAsState()

    MainScaffold(navController = navController, screen = Screen.Cart, viewModel = viewModel) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(SoftWhite)
        ) {
            if (cartItems.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito Vacío", modifier = Modifier.size(64.dp), tint = EmeraldGreen)
                        Spacer(Modifier.height(8.dp))
                        Text("Tu carrito está vacío.", style = MaterialTheme.typography.h6)
                        Text("¡Añade productos frescos del campo!", style = MaterialTheme.typography.body1)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(cartItems, key = { it.product.id }) { item ->
                        val dismissState = rememberDismissState(
                            confirmStateChange = {
                                if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                                    viewModel.removeItemFromCart(item.product)
                                    true
                                } else {
                                    false
                                }
                            }
                        )
                        // Implementación de SwipeToDismiss (Eliminar producto mediante deslizamiento)
                        SwipeToDismiss(
                            state = dismissState,
                            directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd),
                            background = {
                                val color = when (dismissState.targetValue) {
                                    DismissValue.Default -> Color.Transparent
                                    else -> Color.Red.copy(alpha = 0.8f)
                                }
                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .background(color, MaterialTheme.shapes.medium)
                                        .padding(horizontal = 20.dp),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Eliminar", tint = Color.White)
                                }
                            },
                            dismissContent = {
                                CartItemView(
                                    item = item,
                                    onQuantityChange = { newQty ->
                                        viewModel.updateCartItemQuantity(item.product, newQty)
                                    },
                                    onRemoveItem = { viewModel.removeItemFromCart(item.product) }
                                )
                            }
                        )
                    }
                }

                // Resumen y Checkout (Procesamiento de pedidos)
                CartSummary(cartTotal = cartTotal, onCheckout = viewModel::checkout)
            }
        }
    }
}

/**
 * Vista de un solo artículo en el carrito.
 */
@Composable
fun CartItemView(item: CartItem, onQuantityChange: (Int) -> Unit, onRemoveItem: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(item.product.name, style = MaterialTheme.typography.h6.copy(fontSize = 16.sp))
                Text(
                    "CLP $${"%,.0f".format(item.product.price)}",
                    style = MaterialTheme.typography.body1.copy(fontSize = 14.sp)
                )
                Text(
                    "Subtotal: CLP $${"%,.0f".format(item.subtotal)}",
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold, color = EmeraldGreen)
                )
            }

            // Controles de Modificación de Carrito
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { onQuantityChange(item.quantity - 1) },
                    enabled = item.quantity > 1
                ) {
                    Icon(Icons.Filled.RemoveCircle, contentDescription = "Restar", tint = EmeraldGreen)
                }
                Text(
                    item.quantity.toString(),
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.width(30.dp),
                    textAlign = TextAlign.Center
                )
                IconButton(
                    onClick = { onQuantityChange(item.quantity + 1) },
                    enabled = item.quantity < item.product.stock // Límite por stock
                ) {
                    Icon(Icons.Filled.AddCircle, contentDescription = "Sumar", tint = EmeraldGreen)
                }
                IconButton(onClick = onRemoveItem) {
                    Icon(Icons.Filled.Delete, contentDescription = "Eliminar", tint = Color.Red.copy(alpha = 0.7f))
                }
            }
        }
    }
}

/**
 * Componente de resumen del carrito y botón de pago.
 */
@Composable
fun CartSummary(cartTotal: Double, onCheckout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.End
    ) {
        // Mostrar un resumen del carrito con precios detallados y totales
        Text(
            "Total Carrito:",
            style = MaterialTheme.typography.h6.copy(color = DarkGrey)
        )
        Text(
            "CLP $${"%,.0f".format(cartTotal)}",
            style = MaterialTheme.typography.h4.copy(color = EmeraldGreen)
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = onCheckout,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MustardYellow),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Confirmar Pedido y Pagar", color = DarkGrey, style = MaterialTheme.typography.button.copy(fontSize = 18.sp))
            Spacer(Modifier.width(8.dp))
            Icon(Icons.Filled.Done, contentDescription = "Pagar", tint = DarkGrey)
        }
    }
}
