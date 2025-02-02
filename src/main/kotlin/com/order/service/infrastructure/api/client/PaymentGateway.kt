package com.order.service.infrastructure.api.client

import com.order.service.core.entities.Payment
import com.order.service.core.entities.PaymentOrder
import java.math.BigDecimal

interface PaymentGateway {
    fun createPayment(amount: BigDecimal): PaymentOrder
    fun updatePayment(oldPayment: Payment, status: String): Payment?
    fun findPayment(partnerId: Int): Payment
}