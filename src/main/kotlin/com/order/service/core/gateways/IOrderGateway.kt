package com.order.service.core.gateways

import com.order.service.core.entities.CreateOrder
import com.order.service.core.entities.Order
import com.order.service.core.entities.UpdateOrder

interface IOrderGateway {
    fun createOrder(createOrder: CreateOrder): Order
    fun listOrders(): List<Order>
    fun findOrderById(orderId: Int): Order?
    fun updateOrderStatus(oldOrder: Order, order: UpdateOrder): Order
}