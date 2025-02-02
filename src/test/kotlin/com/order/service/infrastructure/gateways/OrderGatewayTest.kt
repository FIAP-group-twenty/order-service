package com.order.service.infrastructure.gateways

import com.order.service.core.entities.CreateOrder
import com.order.service.core.entities.Order
import com.order.service.core.entities.OrderProduct
import com.order.service.core.entities.OrderStatus
import com.mercadopago.resources.payment.Payment
import com.order.service.core.mapper.PaymentMapper
import com.order.service.infrastructure.persistence.entities.OrderItemEntity
import com.order.service.infrastructure.persistence.jpa.IOrderDataSource
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import java.math.BigDecimal

class OrderGatewayTest {

    private lateinit var orderDataSource: IOrderDataSource
    private lateinit var orderGateway: OrderGateway

    @BeforeEach
    fun setup() {
        orderDataSource = mockk(relaxed = true)
        orderGateway = OrderGateway(orderDataSource)
    }

    private val createOrder = CreateOrder(
        1,
        BigDecimal(100.0),
        listOf(OrderProduct(1, 1, BigDecimal(100.0))),
        Payment()
    )

    val order = Order(
        idOrder = 1,
        orderValue = createOrder.totalOrder,
        idCustomer = createOrder.idCustomer,
        status = OrderStatus.FINISHED.name,
        orderItems = listOf(OrderItemEntity(idOrderItem = 1, idProduct = 1, quantity = 1)),
        payment = PaymentMapper.toEntity(1, "aaaaa", "APPROVED", BigDecimal(500))
    )

//    @Test
//    fun `create order`(){
//        val entity = toEntity(createOrder)
//        every { orderDataSource.save(entity) } returns entity
//        val orderSaved = orderGateway.createOrder(createOrder)
//        assertThat(orderSaved).isEqualTo(order)
//    }


}
