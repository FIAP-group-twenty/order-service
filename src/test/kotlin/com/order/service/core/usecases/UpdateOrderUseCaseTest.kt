package com.order.service.core.usecases

import com.order.service.core.entities.Order
import com.order.service.core.entities.PaymentOrder
import com.order.service.core.entities.UpdateOrder
import com.order.service.core.gateways.IOrderGateway
import com.order.service.core.usecase.UpdateOrderUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class UpdateOrderUseCaseTest {

    private lateinit var orderGateway: IOrderGateway
    private lateinit var updateOrderUseCase: UpdateOrderUseCase

    @BeforeEach
    fun setUp() {
        orderGateway = mockk()
        updateOrderUseCase = UpdateOrderUseCase(orderGateway)
    }

    val order = Order(
        idOrder = 1,
        orderValue = BigDecimal(100.0),
        idCustomer = 2,
        status = "PENDING",
        payment = PaymentOrder(1, 1, "123", "success"),
        orderItems = listOf(),
    )

    val updatedOrder = Order(
        idOrder = 1,
        orderValue = BigDecimal(100.0),
        idCustomer = 2,
        status = "SUCCESS",
        payment = PaymentOrder(1, 1, "123", "success"),
        orderItems = listOf(),
    )

    val updateOrder = UpdateOrder(
        status = "SUCCESS",
    )


    @Test
    fun `should update order status successfully`() {
        val oldOrder = order

        every { orderGateway.findOrderById(1) } returns oldOrder
        every { orderGateway.updateOrderStatus(oldOrder, updateOrder) } returns updatedOrder

        val result = updateOrderUseCase.execute(1, updateOrder)

        assertEquals(updatedOrder, result)
        verify(exactly = 1) { orderGateway.findOrderById(1) }
        verify(exactly = 1) { orderGateway.updateOrderStatus(oldOrder, updateOrder) }
    }
}
