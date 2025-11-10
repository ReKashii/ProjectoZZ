package com.example.huertohogar.data.model

/**
 * Clase de datos para representar un artículo dentro del carrito de compras.
 */
data class CartItem(
    val product: Product,
    val quantity: Int
) {
    val subtotal: Double
        get() = product.price * quantity
}
