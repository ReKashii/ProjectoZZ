package com.example.huertohogar.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogar.data.db.AppDatabase
import com.example.huertohogar.data.model.CartItem
import com.example.huertohogar.data.model.Category
import com.example.huertohogar.data.model.Product
import com.example.huertohogar.data.network.RetrofitInstance
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel que maneja la lógica de la aplicación HuertoHogar (MVVM).
 */
class HuertoHogarViewModel(application: Application) : AndroidViewModel(application) {

    private val userSessionRepository = UserSessionRepository(application)
    private val productDao = AppDatabase.getDatabase(application).productDao()
    private val productRepository = ProductRepository(productDao)

    // Estado de autenticación
    val isLoggedIn: StateFlow<Boolean> = userSessionRepository.isLoggedIn
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    // Estado para las Categorías
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    // Categoría seleccionada actualmente
    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory

    // Estado base de productos (desde Room)
    private val allProductsFlow: StateFlow<List<Product>> = productRepository.allProducts
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // Estado del término de búsqueda
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // --- LÓGICA MAESTRA DE FILTRADO ---
    val products: StateFlow<List<Product>> = combine(
        allProductsFlow,
        _searchQuery,
        _selectedCategory
    ) { allProducts, query, category ->
        var filteredList = allProducts

        // 1. Filtrar por texto
        if (query.isNotBlank()) {
            filteredList = filteredList.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
        }

        // 2. Filtrar por Categoría
        if (category != null) {
            filteredList = filteredList.filter {
                it.category.equals(category, ignoreCase = true)
            }
        }

        filteredList
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    // Carrito y Pedidos
    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart: StateFlow<List<CartItem>> = _cart

    private val _orders = MutableStateFlow<List<CartItem>>(emptyList())
    val orders: StateFlow<List<CartItem>> = _orders

    // --- CORRECCIÓN AQUÍ: Cálculo directo, sin archivo externo ---
    val cartTotal: StateFlow<Double> = _cart.map { list ->
        list.sumOf { it.subtotal }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    init {
        viewModelScope.launch {
            productRepository.refreshProducts()
            fetchCategories()
        }
    }

    private suspend fun fetchCategories() {
        try {
            val apiCategories = RetrofitInstance.api.getCategories()
            _categories.value = apiCategories
        } catch (e: Exception) {
            println("Error al cargar categorías: ${e.message}")
        }
    }

    fun selectCategory(categoryName: String) {
        if (_selectedCategory.value == categoryName) {
            _selectedCategory.value = null
        } else {
            _selectedCategory.value = categoryName
        }
    }

    fun register(email: String, p: String, onSuccess: () -> Unit) {
        viewModelScope.launch { if (email.isNotBlank()) { userSessionRepository.setLoggedIn(true); onSuccess() } }
    }

    fun login(email: String, p: String, onSuccess: () -> Unit) {
        viewModelScope.launch { if (email.isNotBlank()) { userSessionRepository.setLoggedIn(true); onSuccess() } }
    }

    fun logout() {
        viewModelScope.launch {
            userSessionRepository.setLoggedIn(false)
            _cart.value = emptyList()
            _orders.value = emptyList()
        }
    }

    fun onSearchQueryChange(query: String) { _searchQuery.value = query }

    fun addToCart(product: Product) {
        _cart.update { current ->
            val existing = current.find { it.product.id == product.id }
            if (existing != null) {
                current.map { if (it.product.id == product.id) it.copy(quantity = it.quantity + 1) else it }
            } else {
                current + CartItem(product, 1)
            }
        }
    }

    fun updateCartItemQuantity(p: Product, q: Int) {
        _cart.update { c -> c.mapNotNull { if (it.product.id == p.id) (if (q > 0) it.copy(quantity = q) else null) else it } }
    }

    fun removeItemFromCart(p: Product) { _cart.update { it.filter { item -> item.product.id != p.id } } }

    fun checkout() {
        _orders.update { it + _cart.value }
        _cart.value = emptyList()
    }
}