package com.order.service.core.usecases

import com.order.service.core.entities.CreateOrder
import com.order.service.core.entities.Order
import com.order.service.core.entities.OrderProduct
import com.order.service.core.entities.OrderStatus.PENDING
import com.mercadopago.resources.payment.Payment
import com.order.service.core.entities.Product
import com.order.service.core.gateways.IOrderGateway
import com.order.service.core.mapper.PaymentMapper
import com.order.service.core.usecase.CreateOrderUseCase
import com.order.service.infrastructure.api.client.PaymentPartnerGateway
import com.order.service.infrastructure.api.client.ProductGateway
import com.order.service.infrastructure.api.client.RedisRepository
import com.order.service.infrastructure.persistence.entities.OrderEntity
import com.order.service.infrastructure.persistence.entities.PaymentEntity
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class CreateOrderUseCaseTest {

    private lateinit var orderGateway: IOrderGateway
    private lateinit var paymentpartnerGateway: PaymentPartnerGateway
    private lateinit var productGateway: ProductGateway
    private lateinit var redisRepository: RedisRepository
    private lateinit var createOrderUseCase: CreateOrderUseCase

    @BeforeEach
    fun setUp() {
        orderGateway = mockk()
        paymentpartnerGateway = mockk()
        productGateway = mockk()
        redisRepository = mockk()
        createOrderUseCase = CreateOrderUseCase(orderGateway, paymentpartnerGateway, productGateway, redisRepository)
    }

    val order = Order(
        idOrder = 1,
        orderValue = BigDecimal(100.0),
        idCustomer = 2,
        status = "SUCCESS",
        payment = PaymentMapper.toEntity(1, "aaaaa" , "APPROVED", BigDecimal(500)),
        orderItems = listOf(),
    )

    private val paymentOrder = Payment()

    @Test
    fun `should correctly assign product prices when creating an order`() {
        val createOrder = CreateOrder(
            123,
            BigDecimal(100.0),
            listOf(OrderProduct(1, 1, BigDecimal(100.00))),
            paymentOrder
        )

        every { paymentpartnerGateway.createPayment(BigDecimal(100.0)) } returns paymentOrder
        every { productGateway.getProductById(1) } returns Product(id = 1, price = BigDecimal(100.0))
        every { orderGateway.createOrder(createOrder) } returns order
        every { redisRepository.save(any(),any()) } just Runs

        createOrderUseCase.execute(createOrder)

        verify(exactly = 1) { productGateway.getProductById(1) }
        verify(exactly = 1) { orderGateway.createOrder(createOrder) }
        verify(exactly = 1) { paymentpartnerGateway.createPayment(any()) }
    }


    @Test
    fun `should throw exception when an error occurs`() {
        val createOrder = CreateOrder(
            123,
            BigDecimal(100.0),
            listOf(OrderProduct(2, 1, BigDecimal(100.00))),
            paymentOrder
        )

        every { productGateway.getProductById(any()) } throws RuntimeException("Unable to complete your order, please try again later.")

        val exception = assertThrows<RuntimeException> {
            createOrderUseCase.execute(createOrder)
        }
        assertEquals("Unable to complete your order, please try again later.", exception.message)
    }

}