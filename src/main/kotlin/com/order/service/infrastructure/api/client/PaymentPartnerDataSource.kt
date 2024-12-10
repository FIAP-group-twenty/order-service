package com.order.service.infrastructure.api.client

import com.mercadopago.resources.payment.Payment
import java.math.BigDecimal

interface PaymentPartnerDataSource {
    fun createPayment(amount: BigDecimal): Payment?
    fun getPayment(paymentId: Int): Payment
}