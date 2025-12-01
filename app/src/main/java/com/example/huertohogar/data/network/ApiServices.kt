package com.example.huertohogar.data.network

import com.example.huertohogar.data.db.ProductEntity
import com.example.huertohogar.data.model.Category // Importar esto
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface HuertoApiService {
    @GET("api/productos")
    suspend fun getProducts(): List<ProductEntity>

    // --- NUEVO: Endpoint de Categorías ---
    @GET("api/categorias")
    suspend fun getCategories(): List<Category>
}

object RetrofitInstance {
    // ... (El resto del objeto RetrofitInstance queda IGUAL, no lo borres) ...
    private const val BASE_URL = "https://api-dfs2-dm-production.up.railway.app/"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val api: HuertoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HuertoApiService::class.java)
    }
}