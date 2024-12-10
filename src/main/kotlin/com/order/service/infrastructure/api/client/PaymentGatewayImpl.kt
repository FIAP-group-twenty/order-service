package com.order.service.infrastructure.api.client

import com.order.service.core.entities.Payment
import com.order.service.core.entities.PaymentOrder
import com.order.service.core.mapper.PaymentMapper.toDto
import com.order.service.core.mapper.PaymentMapper.toEntity
import com.order.service.infrastructure.exceptions.ResourceInternalServerException
import com.order.service.infrastructure.persistence.jpa.PaymentDataSource
import org.apache.velocity.exception.ResourceNotFoundException
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class PaymentGatewayImpl(
    private val paymentDataSource: PaymentDataSource
) : PaymentGateway {
    override fun createPayment(amount: BigDecimal): PaymentOrder {
        TODO("Not yet implemented")
    }

    override fun updatePayment(oldPayment: Payment, status: String): Payment? {
        try {
            val paymentUpdate = oldPayment.toEntity(status) //todo: todo: depois voltar validador do status
            val result = paymentDataSource.save(paymentUpdate)
            return result.toDto()
        } catch (ex: Exception) {
            throw ResourceInternalServerException("Failed to update payment with ID: ${oldPayment.idPay}", ex)
        }
    }

    override fun findPayment(partnerId: Int): Payment {
        try {
            paymentDataSource.findByMercadoPagoId(partnerId)?.let { pay ->
                return pay.toDto()
            }
            throw ResourceNotFoundException("Payment not found")
        } catch (ex: Exception) {
            throw ResourceInternalServerException("Failed to find payment $partnerId.", ex)
        }
    }
}