package com.order.service.api.configuration

import com.order.service.core.usecase.CreateOrderUseCase
import com.order.service.core.usecase.ListOrderUseCase
import com.order.service.core.usecase.UpdateOrderUseCase
import com.order.service.infrastructure.api.client.PaymentGateway
import com.order.service.infrastructure.api.client.PaymentGatewayImpl
import com.order.service.infrastructure.api.client.ProductGateway
import com.order.service.infrastructure.api.client.ProductGatewayImpl
import com.order.service.infrastructure.api.client.RedisRepository
import com.order.service.infrastructure.api.client.RedisRepositoryImpl
import com.order.service.infrastructure.gateways.OrderGateway
import com.order.service.infrastructure.persistence.jpa.IOrderDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class OrderConfiguration(
    private val orderDataSource: IOrderDataSource,
    private val redisTemplate: RedisTemplate<String, String>
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
        return PaymentGatewayImpl()
    }

    @Bean
    fun redisRepository(): RedisRepository {
        return RedisRepositoryImpl(redisTemplate)
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
        return CreateOrderUseCase(orderGateway(), paymentGateway(), productGateway(), redisRepository())
    }
}