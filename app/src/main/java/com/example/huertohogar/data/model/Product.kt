package com.example.huertohogar.data.model

/**
 * Clase de datos para representar un Producto.
 * Nota: 'imageUrl' se usa con un placeholder en este ejemplo, no se cargará una imagen real.
 */
data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val description: String,
    val category: String,
    val stock: Int,
    val imageUrl: String
)
