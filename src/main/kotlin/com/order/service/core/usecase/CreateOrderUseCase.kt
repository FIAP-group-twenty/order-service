package com.order.service.core.usecase

import com.order.service.core.util.CREATE_ORDER_ERROR
import com.order.service.core.entities.CreateOrder
import com.order.service.core.entities.Order
import com.order.service.core.gateways.IOrderGateway
import com.order.service.infrastructure.api.client.PaymentGateway
import com.order.service.infrastructure.api.client.ProductGateway
import com.order.service.infrastructure.exceptions.ResourceInternalServerException

class CreateOrderUseCase(
    private val orderGateway: IOrderGateway,
    private val paymentGateway: PaymentGateway,
    private val productGateway: ProductGateway
) {
    fun execute(createOrder: CreateOrder): Order {
        try {
            createOrder.products.forEach { orderProduct ->
                orderProduct.price = productGateway.getProductById(orderProduct.id).price
            }

            val paymentOrder = paymentGateway.createPayment(createOrder.calculateTotalOrderPrice())
            createOrder.associatePayment(paymentOrder)
            return orderGateway.createOrder(createOrder)
        }catch (ex: Exception){
            throw ResourceInternalServerException(CREATE_ORDER_ERROR, ex)
        }
    }
}