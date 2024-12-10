package com.order.service.core.usecase

import com.order.service.core.util.FIND_PAYMENT_INFORMATION_ERROR
import com.order.service.infrastructure.api.client.PaymentPartnerGateway
import com.order.service.infrastructure.api.entities.PaymentInformation
import com.order.service.infrastructure.exceptions.ResourceBadRequestException
import com.order.service.infrastructure.exceptions.ResourceInternalServerException
import com.order.service.infrastructure.gateways.OrderGateway

class GetPaymentStatusUseCase(
    private val paymentGateway: PaymentPartnerGateway,
    private val orderGateway: OrderGateway
) {
    fun execute(orderId: Int): PaymentInformation {
        try {
            val order = orderGateway.findOrderById(orderId)
            order?.payment?.mercadoPagoId?.let { paymentId ->
                val payment = paymentGateway.getPayment(paymentId)
                return PaymentInformation(order = order, payment = payment)
            } ?: throw ResourceBadRequestException(FIND_PAYMENT_INFORMATION_ERROR)
        } catch (ex: Exception) {
            throw ResourceInternalServerException(exception = ex)
        }
    }
}