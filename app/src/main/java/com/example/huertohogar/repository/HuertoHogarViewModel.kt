package com.example.huertohogar.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogar.data.model.CartItem
import com.example.huertohogar.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel que maneja la lógica de la aplicación HuertoHogar (MVVM).
 */
class HuertoHogarViewModel(application: Application) : AndroidViewModel(application) {

    private val userSessionRepository = UserSessionRepository(application)
    private val productRepository = ProductRepository()

    // Estado de autenticación
    val isLoggedIn: StateFlow<Boolean> = userSessionRepository.isLoggedIn
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    // Lista de productos fijos (simulación de datos)
    private val allProducts = productRepository.getProducts()

    // Estado del catálogo de productos con búsqueda
    private val _products = MutableStateFlow(allProducts)
    val products: StateFlow<List<Product>> = _products

    // Estado del carrito de compras (persistencia simulada en memoria)
    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart: StateFlow<List<CartItem>> = _cart

    // Estado del término de búsqueda
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // Propiedades derivadas del carrito
    val cartTotal: StateFlow<Double> = _cart.map { it.sumOf { item -> item.subtotal } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    /**
     * Simula el proceso de registro.
     */
    fun register(email: String, password: String, onRegisterSuccess: () -> Unit) {
        viewModelScope.launch {
            // Lógica de registro simplificada
            if (email.isNotBlank() && password.length >= 6) {
                userSessionRepository.setLoggedIn(true)
                onRegisterSuccess()
            }
        }
    }

    /**
     * Simula el proceso de inicio de sesión.
     */
    fun login(email: String, password: String, onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            // Lógica de autenticación simplificada
            if (email.isNotBlank() && password.length >= 6) {
                userSessionRepository.setLoggedIn(true)
                onLoginSuccess()
            } else {
                // Manejar error de login
            }
        }
    }

    /**
     * Simula el proceso de cierre de sesión.
     */
    fun logout() {
        viewModelScope.launch {
            userSessionRepository.setLoggedIn(false)
            // Limpiar carrito o cualquier estado de usuario si es necesario
            _cart.value = emptyList()
        }
    }

    /**
     * Aplica el filtro de búsqueda al catálogo de productos.
     */
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        _products.value = allProducts.filter {
            it.name.contains(query, ignoreCase = true) || it.category.contains(query, ignoreCase = true)
        }
    }

    /**
     * Agrega un producto al carrito o incrementa su cantidad.
     */
    fun addToCart(product: Product) {
        _cart.update { currentCart ->
            val existingItem = currentCart.find { it.product.id == product.id }
            if (existingItem != null) {
                // Modificación del carrito (incremento)
                currentCart.map {
                    if (it.product.id == product.id) {
                        it.copy(quantity = it.quantity + 1)
                    } else {
                        it
                    }
                }
            } else {
                // Ingreso de nuevo producto
                currentCart + CartItem(product, 1)
            }
        }
    }

    /**
     * Modifica la cantidad de un artículo en el carrito.
     */
    fun updateCartItemQuantity(product: Product, newQuantity: Int) {
        _cart.update { currentCart ->
            currentCart.mapNotNull {
                if (it.product.id == product.id) {
                    if (newQuantity > 0) {
                        it.copy(quantity = newQuantity)
                    } else {
                        // Eliminar si la cantidad es 0
                        null
                    }
                } else {
                    it
                }
            }
        }
    }

    /**
     * Elimina un artículo del carrito.
     */
    fun removeItemFromCart(product: Product) {
        _cart.update { currentCart ->
            currentCart.filter { it.product.id != product.id }
        }
    }

    /**
     * Simula el envío de la venta al backend para registrar en la BD.
     */
    fun checkout() {
        // Enviar datos del carrito (cart.value) al backend.
        println("Venta Enviada al Backend:")
        _cart.value.forEach {
            println(" - ${it.product.name}: ${it.quantity} x ${it.product.price} = ${it.subtotal} CLP")
        }
        println("TOTAL: ${_cart.value.sumOf { it.subtotal }} CLP")

        // Vaciar carrito
        _cart.value = emptyList()
        // Mostrar notificación de éxito (simulada)
    }
}
