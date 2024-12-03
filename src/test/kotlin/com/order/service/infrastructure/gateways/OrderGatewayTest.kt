package com.order.service.infrastructure.gateways

import com.order.service.core.entities.*
import com.order.service.core.mapper.OrderMapper.toEntity
import com.order.service.infrastructure.persistence.entities.OrderItemEntity
import com.order.service.infrastructure.persistence.jpa.IOrderDataSource
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class OrderGatewayTest {

    private lateinit var orderDataSource: IOrderDataSource
    private lateinit var orderGateway: OrderGateway

    @BeforeEach
    fun setup() {
        orderDataSource = mockk(relaxed = true)
        orderGateway = OrderGateway(orderDataSource)
    }

    val createOrder = CreateOrder(
        1,
        BigDecimal(100.0),
        listOf(OrderProduct(1, 1, BigDecimal(100.0))),
        PaymentOrder(1, 1, "abc", "success")
    )

    val order = Order(
        idOrder = 1,
        orderValue = createOrder.totalOrder,
        idCustomer = createOrder.idCustomer,
        status = OrderStatus.FINISHED.name,
        orderItems = listOf(OrderItemEntity(idOrderItem = 1, idProduct = 1, quantity = 1)),
        payment = PaymentOrder(1, 1, "abc", "success")
    )

//    @Test
//    fun `create order`(){
//        val entity = toEntity(createOrder)
//        every { orderDataSource.save(entity) } returns entity
//        val orderSaved = orderGateway.createOrder(createOrder)
//        assertThat(orderSaved).isEqualTo(order)
//    }


}
