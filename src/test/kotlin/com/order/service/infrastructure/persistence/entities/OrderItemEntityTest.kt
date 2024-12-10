package com.order.service.infrastructure.persistence.entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

class OrderItemEntityTest {

    private lateinit var order: OrderEntity
    private lateinit var orderItem: OrderItemEntity

    @BeforeEach
    fun setUp() {
        order = mock(OrderEntity::class.java)

        orderItem = OrderItemEntity(
            idProduct = 101,
            quantity = 5
        )

        orderItem.order = order
    }

    @Test
    fun `should correctly create OrderItemEntity with correct values`() {
        assertNotNull(orderItem)
        assertEquals(101, orderItem.idProduct)
        assertEquals(5, orderItem.quantity)
        assertNull(orderItem.idOrderItem) // idOrderItem ainda não foi persistido, portanto, deve ser null
    }

    @Test
    fun `should associate OrderEntity with OrderItemEntity correctly`() {
        assertNotNull(orderItem.order)
        assertEquals(order, orderItem.order)
    }

    @Test
    fun `should correctly test toString method of OrderItemEntity`() {
        val expectedString = "OrderItemEntity(idOrderItem=null, idProduct=101, quantity=5)"
        assertEquals(expectedString, orderItem.toString())
    }
}
