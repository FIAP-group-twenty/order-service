package com.order.service.core.usecase

import com.order.service.core.entities.CreateOrder
import com.order.service.core.entities.Order
import com.order.service.core.gateways.IOrderGateway

class CreateOrderUseCase(
    private val orderGateway: IOrderGateway
) {
    fun execute(createOrder: CreateOrder): Order {
        try {
            createOrder.products.forEach { product ->
                //chamar ms de produto aqui
            }
            createOrder.calculateTotalOrderPrice()
            //mandar mensagem para um sqs pra processar o pagamento, ou só chamar o ms de pagamento
            return orderGateway.createOrder(createOrder)
        }catch (ex: Exception){
            throw ex
        }
    }
}