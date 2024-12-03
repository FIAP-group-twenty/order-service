package com.order.service.core.mapper

import com.order.service.core.entities.*
import com.order.service.infrastructure.persistence.entities.OrderEntity
import com.order.service.infrastructure.persistence.entities.OrderItemEntity

object OrderMapper {

    fun toEntity(createOrder: CreateOrder): OrderEntity {
        return OrderEntity(
            orderValue = createOrder.totalOrder,
            idCustomer = createOrder.idCustomer,
            status = OrderStatus.PENDING.name,
            orderItens = createOrder.products.map { productModel ->
                OrderItemEntity(
                    idProduct = productModel.id,
                    quantity = productModel.quantity,
                )
            },
            idPay = createOrder.paymentOrder?.idPay,
            mercadoPagoId = createOrder.paymentOrder?.mercadoPagoId,
            qrCode = createOrder.paymentOrder?.qrCode,
            statusPayment = createOrder.paymentOrder?.statusPayment
        )
    }

    fun toDTO(order: OrderEntity) = Order(
        idOrder = order.idOrder,
        orderValue = order.orderValue,
        idCustomer = order.idCustomer,
        creationOrder = order.creationOrder,
        lastUpdateOrder = order.lastUpdateOrder,
        status = order.status,
        orderItems = order.orderItens,
        payment = PaymentOrder(
            idPay = order.idPay,
            mercadoPagoId = order.mercadoPagoId,
            qrCode = order.qrCode,
            statusPayment = order.statusPayment,
        )
    )

    fun formatterOrderList(orders: List<OrderEntity>): List<Order> {
        return orders.map { order ->
            toDTO(order)
        }
    }

    fun Order.toEntity(dto: UpdateOrder) =
        OrderEntity(
            orderValue = orderValue,
            idCustomer = idCustomer,
            status = dto.status,
            orderItens = orderItems,
            lastUpdateOrder = dto.lastUpdateOrder
        )

}