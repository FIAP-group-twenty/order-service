package com.order.service.api.configuration

import com.order.service.core.usecase.CreateOrderUseCase
import com.order.service.core.usecase.ListOrderUseCase
import com.order.service.core.usecase.UpdateOrderUseCase
import com.order.service.infrastructure.gateways.OrderGateway
import com.order.service.infrastructure.persistence.jpa.IOrderDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrderConfiguration(
    private val orderDataSource: IOrderDataSource
) {

    @Bean
    fun orderGateway(): OrderGateway {
        return OrderGateway(orderDataSource)
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
        return CreateOrderUseCase(orderGateway())
    }
}