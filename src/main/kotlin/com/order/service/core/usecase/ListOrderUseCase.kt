package com.order.service.core.usecase

import com.order.service.core.entities.Order
import com.order.service.core.gateways.IOrderGateway

class ListOrderUseCase(
    private val orderGateway: IOrderGateway
) {

    fun execute(): List<Order> {
        return orderGateway.listOrders()
    }

}