package com.order.service.infrastructure.api.entities

import com.mercadopago.resources.payment.Payment
import com.order.service.core.entities.Order

data class PaymentInformation(
    val payment: Payment,
    val order: Order
)
