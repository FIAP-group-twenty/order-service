package com.order.service.core.usecases

import com.order.service.core.entities.Order
import com.order.service.core.entities.PaymentOrder
import com.order.service.core.gateways.IOrderGateway
import com.order.service.core.mapper.PaymentMapper
import com.order.service.core.usecase.ListOrderUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ListOrderUseCaseTest {

    private lateinit var orderGateway: IOrderGateway
    private lateinit var listOrderUseCase: ListOrderUseCase

    @BeforeEach
    fun setUp() {
        orderGateway = mockk()
        listOrderUseCase = ListOrderUseCase(orderGateway)
    }

    val order = Order(
        idOrder = 1,
        orderValue = BigDecimal(100.0),
        idCustomer = 2,
        status = "SUCCESS",
        payment = PaymentMapper.toEntity(1, "aaaaa", "APPROVED", BigDecimal(500)),
        orderItems = listOf(),
    )

    @Test
    fun `should return list of orders from order gateway`() {
        val mockOrders = listOf(order)
        every { orderGateway.listOrders() } returns mockOrders

        val result = listOrderUseCase.execute()

        assertEquals(mockOrders, result)
        verify(exactly = 1) { orderGateway.listOrders() }
    }

    @Test
    fun `should return empty list when no orders exist`() {
        every { orderGateway.listOrders() } returns emptyList()

        val result = listOrderUseCase.execute()

        assertTrue(result.isEmpty())
        verify(exactly = 1) { orderGateway.listOrders() }
    }

}
