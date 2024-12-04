package com.order.service.core.mapper


import com.mercadopago.resources.payment.Payment
import com.order.service.core.entities.CreateOrder
import com.order.service.core.entities.Order
import com.order.service.core.entities.OrderStatus.CANCELED
import com.order.service.core.entities.OrderStatus.STARTED
import com.order.service.core.entities.PaymentStatus.APPROVED
import com.order.service.core.entities.PaymentStatus.DENIED
import com.order.service.core.entities.PaymentStatus.PENDING
import com.order.service.core.entities.UpdateOrder
import com.order.service.infrastructure.persistence.entities.OrderEntity
import com.order.service.infrastructure.persistence.entities.OrderItemEntity
import com.order.service.infrastructure.persistence.entities.PaymentEntity
import java.math.BigDecimal

object OrderMapper {

    fun toEntity(createOrder: CreateOrder): OrderEntity {
        val orderStatus = orderStatus(createOrder.payment)
        val paymentStatus = paymentStatus(createOrder.payment)

        return OrderEntity(
            orderValue = createOrder.totalOrder,
            idCustomer = createOrder.idCustomer,
            status = orderStatus,
            orderItens = createOrder.products.map { productModel ->
                OrderItemEntity(
                    idProduct = productModel.id,
                    quantity = productModel.quantity,
                )
            },
            payment = PaymentEntity(
                mercadoPagoId = createOrder.payment?.id?.toInt(),
                qrCode = createOrder.payment?.pointOfInteraction?.transactionData?.qrCode,
                status = paymentStatus,
                payValue = createOrder.totalOrder
            )
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
        payment = order.payment
    )

    fun Order.toEntity() =
        OrderEntity(
            orderValue = orderValue,
            idCustomer = idCustomer,
            status = status,
            orderItens = orderItems,
            payment = payment,
            lastUpdateOrder = lastUpdateOrder
        )

    fun Order.toEntity(dto: UpdateOrder) =
        OrderEntity(
            orderValue = orderValue,
            idCustomer = idCustomer,
            status = dto.status,
            orderItens = orderItems,
            payment = payment,
            lastUpdateOrder = dto.lastUpdateOrder
        )

    fun formatterOrderList(orders: List<OrderEntity>): List<Order> {
        return orders.map { order ->
            toDTO(order)
        }
    }

    fun status(payment: Payment, orderValue: BigDecimal): UpdateOrder {
        val orderStatus = orderStatus(payment)
        val paymentStatus = paymentStatus(payment)

        return UpdateOrder(
            status = orderStatus, payment = PaymentEntity(
                qrCode = payment.pointOfInteraction?.transactionData?.qrCode,
                status = paymentStatus,
                payValue = orderValue
            )
        )
    }

    private fun paymentStatus(payment: Payment?): String {
        return when (payment?.status) {
            "approved" -> APPROVED.name
            "rejected" -> DENIED.name
            else -> PENDING.name
        }
    }

    private fun orderStatus(payment: Payment?): String {
        return when (payment?.status) {
            "approved" -> STARTED.name
            "rejected" -> CANCELED.name
            else -> PENDING.name
        }
    }

    fun toUpdateOrderRequest(payStatus: String): UpdateOrder {
        val status = when (payStatus) {
            APPROVED.name -> STARTED.name
            DENIED.name -> CANCELED.name
            else -> PENDING.name
        }

        return UpdateOrder(status = status)
    }

}