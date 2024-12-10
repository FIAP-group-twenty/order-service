package com.order.service.core.usecase

import com.mercadopago.resources.payment.Payment
import com.order.service.infrastructure.api.client.PaymentPartnerGateway
import com.order.service.infrastructure.exceptions.ResourceInternalServerException
import java.math.BigDecimal

class CreatePaymentUseCase(private val gateway: PaymentPartnerGateway) {
    fun execute(amount: BigDecimal): Payment {
        gateway.createPayment(amount)?.let { paymentOrder ->
            return paymentOrder
        }

        throw ResourceInternalServerException()
    }
}