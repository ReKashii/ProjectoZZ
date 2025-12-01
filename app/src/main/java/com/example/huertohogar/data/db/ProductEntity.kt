package com.example.huertohogar.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.example.huertohogar.data.model.Product

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    @SerializedName("id")
    val id: String,

    @SerializedName("nombre") // Mapea "nombre" del JSON a "name"
    val name: String,

    @SerializedName("precio") // Mapea "precio" del JSON a "price"
    val price: Double,

    @SerializedName("descripcion")
    val description: String,

    @SerializedName("categoria_nombre") // Aprovecha este campo nuevo
    val category: String = "General",

    @SerializedName("stock")
    val stock: Int,

    @SerializedName("imagen") // Mapea "imagen" del JSON a "imageUrl"
    val imageUrl: String
)

fun ProductEntity.toProductModel() = Product(
    id = id,
    name = name,
    price = price,
    description = description,
    category = category,
    stock = stock,
    imageUrl = imageUrl
)