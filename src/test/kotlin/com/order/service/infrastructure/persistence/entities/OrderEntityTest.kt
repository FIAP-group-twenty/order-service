package com.order.service.infrastructure.persistence.entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class OrderEntityTest {

    @Test
    fun `should create OrderEntity correctly`() {
        val order = OrderEntity(
            orderValue = BigDecimal("100.00"),
            idCustomer = 1,
            status = "NEW",
            orderItens = emptyList()
        )

        assertNull(order.idOrder)
        assertEquals(BigDecimal("100.00"), order.orderValue)
        assertEquals(1, order.idCustomer)
        assertEquals("NEW", order.status)
        assertNotNull(order.creationOrder)
        assertNotNull(order.lastUpdateOrder)
    }

    @Test
    fun `should format OrderEntity correctly`() {
        val orderItem = OrderItemEntity(idOrderItem = null, order = null, idProduct = 1, quantity = 2)

        val order = OrderEntity(
            orderValue = BigDecimal("150.00"),
            idCustomer = 2,
            status = "PROCESSING",
            orderItens = listOf(orderItem),
            idPay = 1,
            mercadoPagoId = 1,
            qrCode = "QRCode",
            statusPayment = "PAID"
        )

        val formattedOrder = order.formatter(order)

        assertEquals(formattedOrder.orderItens[0].order, formattedOrder)
    }

    @Test
    fun `should correctly format OrderEntity with multiple items`() {
        val orderItem1 = OrderItemEntity(idOrderItem = null, order = null, idProduct = 1, quantity = 2)
        val orderItem2 = OrderItemEntity(idOrderItem = null, order = null, idProduct = 2, quantity = 2)

        val order = OrderEntity(
            orderValue = BigDecimal("200.00"),
            idCustomer = 3,
            status = "SHIPPED",
            orderItens = listOf(orderItem1, orderItem2),
            idPay = 2,
            mercadoPagoId = 2,
            qrCode = "QRCode123",
            statusPayment = "PAID"
        )

        val formattedOrder = order.formatter(order)

        assertEquals(formattedOrder.orderItens[0].order, order)
        assertEquals(formattedOrder.orderItens[1].order, order)
    }

    @Test
    fun `should override toString correctly`() {
        val order = OrderEntity(
            orderValue = BigDecimal("300.00"),
            idCustomer = 4,
            status = "DELIVERED",
            orderItens = emptyList()
        )

        val expectedToString = "OrderEntity(idOrder=null, orderValue=300.00, idCustomer=4, status=DELIVERED)"
        assertEquals(expectedToString, order.toString())
    }

    @Test
    fun `should not be null when creating with required fields`() {
        val order = OrderEntity(
            orderValue = BigDecimal("250.00"),
            idCustomer = 5,
            status = "PENDING",
            orderItens = emptyList()
        )

        assertNotNull(order)
        assertEquals(BigDecimal("250.00"), order.orderValue)
        assertEquals(5, order.idCustomer)
        assertEquals("PENDING", order.status)
    }
}
