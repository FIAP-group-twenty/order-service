package com.order.service.infrastructure.api.entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ProductTest {

    @Test
    fun `should create Product instance correctly`() {
        val product = Product(
            id = 1,
            name = "Product 1",
            category = "Electronics",
            price = BigDecimal("19.99"),
            description = "A cool electronic product"
        )

        assertEquals(1, product.id)
        assertEquals("Product 1", product.name)
        assertEquals("Electronics", product.category)
        assertEquals(BigDecimal("19.99"), product.price)
        assertEquals("A cool electronic product", product.description)
    }

    @Test
    fun `should handle default values correctly`() {
        val product = Product()

        assertNull(product.id)
        assertNull(product.name)
        assertNull(product.category)
        assertEquals(BigDecimal.ZERO, product.price)
        assertNull(product.description)
    }

    @Test
    fun `should use copy method correctly`() {
        val originalProduct = Product(
            id = 2,
            name = "Product 2",
            category = "Home Appliances",
            price = BigDecimal("49.99"),
            description = "An essential appliance"
        )

        val updatedProduct = originalProduct.copy(name = "Updated Product 2", price = BigDecimal("59.99"))

        assertEquals("Updated Product 2", updatedProduct.name)
        assertEquals(BigDecimal("59.99"), updatedProduct.price)
        assertEquals(2, updatedProduct.id)
        assertEquals("Home Appliances", updatedProduct.category)
        assertEquals("An essential appliance", updatedProduct.description)
    }

    @Test
    fun `should compare products correctly`() {
        val product1 = Product(id = 3, name = "Product 3", category = "Furniture", price = BigDecimal("99.99"))
        val product2 = Product(id = 3, name = "Product 3", category = "Furniture", price = BigDecimal("99.99"))

        assertEquals(product1, product2)

        val product3 = product2.copy(name = "Updated Product 3")
        assertNotEquals(product1, product3)
    }
}
