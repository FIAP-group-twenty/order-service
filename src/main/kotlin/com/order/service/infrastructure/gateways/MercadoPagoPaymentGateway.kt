package com.order.service.infrastructure.gateways

import com.mercadopago.resources.payment.Payment
import com.order.service.infrastructure.api.client.PaymentPartnerDataSource
import com.order.service.infrastructure.api.client.PaymentPartnerGateway
import com.order.service.infrastructure.exceptions.ResourceInternalServerException
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class MercadoPagoPaymentGateway(
    private val dataSource: PaymentPartnerDataSource
) : PaymentPartnerGateway {

    override fun getPayment(paymentId: Int): Payment {
        try {
            return dataSource.getPayment(paymentId)
        } catch (ex: Exception) {
            throw ResourceInternalServerException(exception = ex)
        }

    }

    override fun createPayment(amount: BigDecimal): Payment? {
        return try {
            dataSource.createPayment(amount)
        } catch (ex: Exception) {
            null
        }
    }
}