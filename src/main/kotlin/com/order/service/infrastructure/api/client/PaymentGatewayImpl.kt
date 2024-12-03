package com.order.service.infrastructure.api.client

import com.order.service.core.entities.PaymentOrder
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class PaymentGatewayImpl : PaymentGateway {
    override fun createPayment(amount: BigDecimal): PaymentOrder {
        TODO("Not yet implemented")
    }
}