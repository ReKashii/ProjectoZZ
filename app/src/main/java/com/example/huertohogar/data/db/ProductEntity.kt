package com.example.huertohogar.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad de Room para la tabla de Productos.
 * Almacena los datos de los productos en la base de datos local.
 */
@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val price: Double,
    val description: String,
    val category: String,
    val stock: Int,
    val imageUrl: String
)

// Extension function para convertir de Entity a Model (para usar en ViewModel)
fun ProductEntity.toProductModel() = com.example.huertohogar.data.model.Product(
    id = id,
    name = name,
    price = price,
    description = description,
    category = category,
    stock = stock,
    imageUrl = imageUrl
)