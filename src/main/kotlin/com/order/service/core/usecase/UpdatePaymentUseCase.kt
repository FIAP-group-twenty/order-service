package com.order.service.core.usecase


import com.order.service.core.entities.Payment
import com.order.service.core.gateways.IOrderGateway
import com.order.service.core.mapper.OrderMapper.toUpdateOrderRequest
import com.order.service.infrastructure.api.client.PaymentGateway
import com.order.service.infrastructure.api.client.PaymentPartnerGateway
import com.order.service.infrastructure.exceptions.ResourceInternalServerException

class UpdatePaymentUseCase(
    private val mercadoPagoGateway: PaymentPartnerGateway,
    private val paymentGateway: PaymentGateway,
    private val orderGateway: IOrderGateway,
) {
    fun execute(mercadoPagoId: Int): Payment {
        try {
            val paymentMP = mercadoPagoGateway.getPayment(mercadoPagoId)
            val payment = paymentGateway.findPayment(mercadoPagoId)

            paymentGateway.updatePayment(payment, paymentMP.status)?.let {
                orderGateway.updateOrderStatus(it.order!!, toUpdateOrderRequest(it.status))
            }

            return payment

        } catch (ex: Exception) {
            throw ResourceInternalServerException(exception = ex)
        }
    }
}