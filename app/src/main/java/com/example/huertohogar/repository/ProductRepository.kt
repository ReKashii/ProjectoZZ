package com.example.huertohogar.repository

import com.example.huertohogar.data.db.ProductDao
import com.example.huertohogar.data.db.ProductEntity
import com.example.huertohogar.data.db.toProductModel
import com.example.huertohogar.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first 
import kotlinx.coroutines.flow.map


class ProductRepository(private val productDao: ProductDao) {

  
    private fun getStaticProducts(): List<ProductEntity> {
       
        return listOf(
            ProductEntity(
                id = "FR001", name = "Manzanass Fuji", description = "Manzanas dulces y crujientes, perfectas para un snack.",
                price = 2000.0, stock = 120, category = "Frutas Frescas",
                imageUrl = "https://santaisabel.vtexassets.com/arquivos/ids/174684-900-900?width=900&height=900&aspect=true"
            ),
            ProductEntity(
                id = "FR002", name = "Naranjas Valencia", description = "Jugosas naranjas valencianas, ideales para zumos.",
                price = 1500.0, stock = 150, category = "Frutas Frescas",
                imageUrl = "https://santaisabel.vtexassets.com/arquivos/ids/162932-900-900?width=900&height=900&aspect=true"
            ),
            ProductEntity(
                id = "FR003", name = "Plátanos Cavendish", description = "Plátanos maduros y llenos de energía.",
                price = 900.0, stock = 200, category = "Frutas Frescas",
                imageUrl = "https://santaisabel.vtexassets.com/arquivos/ids/169527-900-900?width=900&height=900&aspect=true"
            ),
            ProductEntity(
                id = "VR001", name = "Zanahorias Orgánicas", description = "Zanahorias frescas y orgánicas, ricas en vitaminas.",
                price = 1100.0, stock = 90, category = "Verduras Orgánicas",
                imageUrl = "https://santaisabel.vtexassets.com/arquivos/ids/161035-900-900?width=900&height=900&aspect=true"
            ),
            ProductEntity(
                id = "VR002", name = "Espinacas Frescas", description = "Espinacas tiernas y frescas, perfectas para ensaladas.",
                price = 1300.0, stock = 70, category = "Verduras Orgánicas",
                imageUrl = "https://santaisabel.vtexassets.com/arquivos/ids/161028-900-900?width=900&height=900&aspect=true"
            ),
            ProductEntity(
                id = "PL001", name = "Leche Entera", description = "Leche fresca y entera de vaca, ideal para toda la familia.",
                price = 1000.0, stock = 90, category = "Lácteos",
                imageUrl = "https://santaisabel.vtexassets.com/arquivos/ids/295371-900-900?width=900&height=900&aspect=true"
            )
        )
    }


    val allProducts: Flow<List<Product>> = productDao.getAllProducts().map { entities ->
        entities.map { it.toProductModel() }
    }

 
    suspend fun populateDatabase() {
        if (productDao.getAllProducts().first().isEmpty()) {
            productDao.insertAll(getStaticProducts())
        }
    }
}
