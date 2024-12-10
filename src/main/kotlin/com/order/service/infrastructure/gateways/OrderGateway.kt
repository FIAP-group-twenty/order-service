package com.order.service.infrastructure.gateways

import com.order.service.core.entities.CreateOrder
import com.order.service.core.entities.Order
import com.order.service.core.entities.UpdateOrder
import com.order.service.core.gateways.IOrderGateway
import com.order.service.core.mapper.OrderMapper.formatterOrderList
import com.order.service.core.mapper.OrderMapper.toDTO
import com.order.service.core.mapper.OrderMapper.toEntity
import com.order.service.core.util.CREATE_ORDER_ERROR
import com.order.service.infrastructure.exceptions.ResourceInternalServerException
import com.order.service.infrastructure.persistence.jpa.IOrderDataSource
import org.springframework.stereotype.Repository

@Repository
class OrderGateway(
    private val orderDataSource: IOrderDataSource
): IOrderGateway {

    override fun createOrder(createOrder: CreateOrder): Order {
       try {
           val createNewOrder = toEntity(createOrder)
           val saveOrder = orderDataSource.save(createNewOrder.formatter(createNewOrder))
           return toDTO(saveOrder)
       }catch (ex: Exception){
           throw ResourceInternalServerException(CREATE_ORDER_ERROR, ex)

       }
    }

    override fun listOrders(): List<Order> {
        try {
            val orders = orderDataSource.findOrdersByStatusAndCreationTime()
            return formatterOrderList(orders)
        } catch (ex: Exception) {
            throw ResourceInternalServerException("Unable to list orders, please try again later.", ex)
        }
    }

    override fun findOrderById(orderId: Int): Order? {
        try {
            return orderDataSource.findByIdOrder(orderId)?.let { order ->
                toDTO(order)
            }
        } catch (ex: Exception) {
            throw ResourceInternalServerException("Failed to find order $orderId.", ex)
        }
    }

    override fun updateOrderStatus(oldOrder: Order, order: UpdateOrder): Order {
        try {
            val orderUpdate = oldOrder.toEntity(order)
            val result = orderDataSource.save(orderUpdate)
            return toDTO(result)
        } catch (ex: Exception) {
            throw ResourceInternalServerException("Failed to update order with ID: ${oldOrder.idOrder}", ex)
        }
    }
}