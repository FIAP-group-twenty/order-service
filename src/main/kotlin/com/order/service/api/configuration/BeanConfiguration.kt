package com.order.service.api.configuration

import com.order.service.core.usecase.CreateOrderUseCase
import com.order.service.core.usecase.CreatePaymentUseCase
import com.order.service.core.usecase.GetPaymentStatusUseCase
import com.order.service.core.usecase.ListOrderUseCase
import com.order.service.core.usecase.UpdateOrderUseCase
import com.order.service.core.usecase.UpdatePaymentUseCase
import com.order.service.infrastructure.api.client.PaymentGateway
import com.order.service.infrastructure.api.client.PaymentGatewayImpl
import com.order.service.infrastructure.api.client.PaymentPartnerDataSource
import com.order.service.infrastructure.api.client.PaymentPartnerGateway
import com.order.service.infrastructure.api.client.ProductGateway
import com.order.service.infrastructure.api.client.ProductGatewayImpl
import com.order.service.infrastructure.api.client.RedisRepository
import com.order.service.infrastructure.api.client.RedisRepositoryImpl
import com.order.service.infrastructure.gateways.MercadoPagoPaymentGateway
import com.order.service.infrastructure.gateways.OrderGateway
import com.order.service.infrastructure.persistence.jpa.IOrderDataSource
import com.order.service.infrastructure.persistence.jpa.PaymentDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class BeanConfiguration(
    private val orderDataSource: IOrderDataSource,
    private val paymentDataSource: PaymentDataSource,
    private val redisTemplate: RedisTemplate<String, String>,
    private val paymentPartnerSource: PaymentPartnerDataSource
) {

    @Bean
    fun orderGateway(): OrderGateway {
        return OrderGateway(orderDataSource)
    }

    @Bean
    fun productGateway(): ProductGateway {
        return ProductGatewayImpl()
    }

    @Bean
    fun paymentGateway(): PaymentGateway {
        return PaymentGatewayImpl(paymentDataSource)
    }

    @Bean
    fun redisRepository(): RedisRepository {
        return RedisRepositoryImpl(redisTemplate)
    }

    @Bean
    fun paymentPartnerGateway() : PaymentPartnerGateway{
        return MercadoPagoPaymentGateway(paymentPartnerSource)
    }

    @Bean
    fun listOrder(): ListOrderUseCase {
        return ListOrderUseCase(orderGateway())
    }

    @Bean
    fun updateOrder(): UpdateOrderUseCase {
        return UpdateOrderUseCase(orderGateway())
    }

    @Bean
    fun createOrderUseCase(): CreateOrderUseCase {
        return CreateOrderUseCase(orderGateway(), paymentPartnerGateway(), productGateway(), redisRepository())
    }

    @Bean
    fun createPaymentUseCase(): CreatePaymentUseCase {
        return CreatePaymentUseCase(paymentPartnerGateway())
    }

    @Bean
    fun getPaymentStatusUseCase(): GetPaymentStatusUseCase {
        return GetPaymentStatusUseCase(paymentPartnerGateway(), orderGateway())
    }

    @Bean
    fun updatePaymentUseCase(): UpdatePaymentUseCase {
        return UpdatePaymentUseCase(paymentPartnerGateway(), paymentGateway(), orderGateway())
    }
}