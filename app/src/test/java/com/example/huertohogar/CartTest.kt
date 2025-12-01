package com.example.huertohogar

// --- ESTAS SON LAS LÍNEAS QUE TE FALTABAN PARA QUE NO SALGA ROJO ---
import com.example.huertohogar.data.model.CartItem
import com.example.huertohogar.data.model.Product
import org.junit.Assert.assertEquals
import org.junit.Test
// ------------------------------------------------------------------

class CartTest {

    // Prueba 1: Verificar que el subtotal se calcula bien
    @Test
    fun `subtotal calculate correctly`() {
        val product = Product("1", "Pera", 100.0, "Desc", "Cat", 10, "")
        val item = CartItem(product, 5) // 100 * 5 = 500

        assertEquals(500.0, item.subtotal, 0.0)
    }

    // Prueba 2: Verificar lógica simple
    @Test
    fun `basic math check`() {
        assertEquals(4, 2 + 2)
    }
}