package com.order.service.core.entities

data class PaymentOrder(
    var idPay: Int? = null,
    var mercadoPagoId: Int? = null,
    var qrCode: String? = null,
    var statusPayment: String? = null,
)