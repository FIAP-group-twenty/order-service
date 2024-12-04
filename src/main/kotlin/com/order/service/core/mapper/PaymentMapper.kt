package com.order.service.core.mapper

import com.order.service.core.entities.Payment
import com.order.service.core.entities.PaymentStatus.PENDING
import com.order.service.core.entities.UpdateOrder
import com.order.service.core.mapper.OrderMapper.toEntity
import com.order.service.infrastructure.persistence.entities.PaymentEntity
import java.math.BigDecimal
import java.time.LocalDateTime

object PaymentMapper {

    fun Payment.toEntity(newStatus: String): PaymentEntity {
        return PaymentEntity(
            idPay = idPay,
            mercadoPagoId = mercadoPagoId,
            order = order?.toEntity(UpdateOrder(order.status)),
            qrCode = qrCode,
            status = newStatus,
            payValue = payValue,
            creationPay = creationOrder,
            lastUpdatePay = LocalDateTime.now()
        )
    }

    fun toEntity(
        mercadoPagoId: Int,
        qrCode: String,
        status: String,
        payValue: BigDecimal,
        creationPay: LocalDateTime? = null,
        lastUpdatePay: LocalDateTime? = null,
    ) = PaymentEntity(
        mercadoPagoId = mercadoPagoId,
        qrCode = qrCode,
        status = status,
        payValue = payValue,
        creationPay = creationPay  ?: LocalDateTime.now(),
        lastUpdatePay = lastUpdatePay ?: LocalDateTime.now(),
    )

    fun PaymentEntity.toDto(): Payment {
        return Payment(
            idPay = idPay,
            mercadoPagoId = mercadoPagoId,
            order = order?.let { myOrder ->
                OrderMapper.toDTO(myOrder)
            },
            qrCode = qrCode,
            status = status ?: PENDING.name,
            payValue = payValue ?: BigDecimal.ZERO,
            creationOrder = creationPay,
            lastUpdateOrder = lastUpdatePay
        )
    }
}