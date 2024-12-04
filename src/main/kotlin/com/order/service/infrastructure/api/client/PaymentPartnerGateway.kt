package com.order.service.infrastructure.api.client

import com.mercadopago.resources.payment.Payment
import java.math.BigDecimal

interface PaymentPartnerGateway {
    fun getPayment(paymentId: Int): Payment
    fun createPayment(amount: BigDecimal): Payment?
}