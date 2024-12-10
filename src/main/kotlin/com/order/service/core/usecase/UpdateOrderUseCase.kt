package com.order.service.core.usecase

import com.order.service.core.entities.Order
import com.order.service.core.entities.UpdateOrder
import com.order.service.core.gateways.IOrderGateway

class UpdateOrderUseCase(
    private val orderGateway: IOrderGateway
) {

    fun execute(id: Int, updateOrder: UpdateOrder): Order {
        orderGateway.findOrderById(id).let { oldOrder ->
            return orderGateway.updateOrderStatus(oldOrder!!, updateOrder)
        }
    }
}