package com.example.huertohogar.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogar.data.db.AppDatabase
import com.example.huertohogar.data.model.CartItem
import com.example.huertohogar.data.model.Product
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel que maneja la lógica de la aplicación HuertoHogar (MVVM).
 */
class HuertoHogarViewModel(application: Application) : AndroidViewModel(application) {

    private val userSessionRepository = UserSessionRepository(application)
    private val productDao = AppDatabase.getDatabase(application).productDao() // Obtener DAO
    private val productRepository = ProductRepository(productDao) // Inyectar DAO en Repository

    // Estado de autenticación
    val isLoggedIn: StateFlow<Boolean> = userSessionRepository.isLoggedIn
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    // Estado para contener TODOS los productos (desde Room)
    private val allProductsFlow: StateFlow<List<Product>> = productRepository.allProducts
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // Estado del término de búsqueda
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // Estado FINAL del catálogo de productos (Filtrado + Room)
    val products: StateFlow<List<Product>> = combine(
        allProductsFlow,
        _searchQuery
    ) { allProducts, query ->
        if (query.isBlank()) {
            allProducts
        } else {
            allProducts.filter {
                it.name.contains(query, ignoreCase = true) || it.category.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    // Estado del carrito de compras (persistencia SIMULADA en memoria, el requisito era Room para datos)
    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart: StateFlow<List<CartItem>> = _cart

    // Propiedades derivadas del carrito
    val cartTotal: StateFlow<Double> = _cart.map { it.sumOf { item -> item.subtotal } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    init {
        // Inicializar la base de datos de Room con datos estáticos
        viewModelScope.launch {
            productRepository.populateDatabase()
        }
    }

    /**
     * ... (Funciones register, login, logout, onSearchQueryChange, addToCart, etc. SIN CAMBIOS LÓGICOS)
     */

    fun register(email: String, password: String, onRegisterSuccess: () -> Unit) {
        viewModelScope.launch {
            if (email.isNotBlank() && password.length >= 6) {
                userSessionRepository.setLoggedIn(true)
                onRegisterSuccess()
            }
        }
    }

    fun login(email: String, password: String, onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            if (email.isNotBlank() && password.length >= 6) {
                userSessionRepository.setLoggedIn(true)
                onLoginSuccess()
            } else {
                // Manejar error de login
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userSessionRepository.setLoggedIn(false)
            _cart.value = emptyList()
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        // La lógica de filtrado ahora se maneja en el 'combine' de arriba.
    }

    fun addToCart(product: Product) {
        _cart.update { currentCart ->
            val existingItem = currentCart.find { it.product.id == product.id }
            if (existingItem != null) {
                currentCart.map {
                    if (it.product.id == product.id) {
                        it.copy(quantity = it.quantity + 1)
                    } else {
                        it
                    }
                }
            } else {
                currentCart + CartItem(product, 1)
            }
        }
    }

    fun updateCartItemQuantity(product: Product, newQuantity: Int) {
        _cart.update { currentCart ->
            currentCart.mapNotNull {
                if (it.product.id == product.id) {
                    if (newQuantity > 0) {
                        it.copy(quantity = newQuantity)
                    } else {
                        null
                    }
                } else {
                    it
                }
            }
        }
    }

    fun removeItemFromCart(product: Product) {
        _cart.update { currentCart ->
            currentCart.filter { it.product.id != product.id }
        }
    }

    fun checkout() {
        println("Venta Enviada al Backend:")
        _cart.value.forEach {
            println(" - ${it.product.name}: ${it.quantity} x ${it.product.price} = ${it.subtotal} CLP")
        }
        println("TOTAL: ${_cart.value.sumOf { it.subtotal }} CLP")

        _cart.value = emptyList()
    }
}