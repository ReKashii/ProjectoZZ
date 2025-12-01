package com.example.huertohogar.repository

import android.util.Log
import com.example.huertohogar.data.db.ProductDao
import com.example.huertohogar.data.db.ProductEntity
import com.example.huertohogar.data.db.toProductModel
import com.example.huertohogar.data.model.Product
import com.example.huertohogar.data.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ProductRepository(private val productDao: ProductDao) {

    // La UI escucha SIEMPRE a la base de datos local
    val allProducts: Flow<List<Product>> = productDao.getAllProducts().map { entities ->
        entities.map { it.toProductModel() }
    }

    // Esta función descarga de la API y guarda en la base de datos
    suspend fun refreshProducts() {
        try {
            val remoteProducts = RetrofitInstance.api.getProducts()
            if (remoteProducts.isNotEmpty()) {
                productDao.insertAll(remoteProducts)
                Log.d("HuertoApp", "Éxito: ${remoteProducts.size} productos descargados.")
            }
        } catch (e: Exception) {
            Log.e("HuertoApp", "Error de API: ${e.message}")
            // Si falla internet y no hay nada guardado, usamos datos falsos para que no se vea vacío
            if (productDao.getAllProducts().first().isEmpty()) {
                productDao.insertAll(getStaticFallback())
            }
        }
    }

    private fun getStaticFallback(): List<ProductEntity> {
        return listOf(
            ProductEntity(
                id = "1",
                name = "Manzanas Fuji",  // Antes decía "Manzana Offline"
                price = 1200.0,
                description = "Directo del campo a tu mesa.",
                category = "Frutas",
                stock = 50,
                // Usamos una imagen real de internet para que se vea profesional
                imageUrl = "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6"
            ),
            ProductEntity(
                id = "2",
                name = "Lechuga Costina",
                price = 890.0,
                description = "Hidropónica y fresca.",
                category = "Verduras",
                stock = 30,
                imageUrl = "https://images.unsplash.com/photo-1622206151226-18ca2c9ab4a1"
            )
        )
    }
}