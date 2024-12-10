package com.order.service.api.presenters

import com.order.service.core.entities.Payment
import com.order.service.core.entities.PaymentStatus.APPROVED
import com.order.service.core.entities.PaymentStatus.DENIED
import com.order.service.core.entities.PaymentStatus.PENDING
import com.order.service.infrastructure.api.entities.PaymentInformation

object PaymentPresenter {

    fun formatterPayment(paymentInformation: PaymentInformation): Payment =
        Payment(
            idPay = paymentInformation.payment.id.toInt(),
            order = paymentInformation.order,
            qrCode = paymentInformation.payment.pointOfInteraction?.transactionData?.qrCode,
            status = getStatus(paymentInformation.payment.status),
            payValue = paymentInformation.order.orderValue,
            creationOrder = paymentInformation.order.creationOrder,
            lastUpdateOrder = paymentInformation.order.lastUpdateOrder
        )


    private fun getStatus(status: String): String = when (status) {
        "approved" -> APPROVED.name
        "rejected" -> DENIED.name
        else -> PENDING.name
    }
}