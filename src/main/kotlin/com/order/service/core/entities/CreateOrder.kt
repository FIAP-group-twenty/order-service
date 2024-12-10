package com.order.service.core.entities

import com.mercadopago.resources.payment.Payment
import java.math.BigDecimal

data class CreateOrder(
    var idCustomer: Int? = null,
    var totalOrder: BigDecimal = BigDecimal.ZERO,
    var products: List<OrderProduct>,
    var payment: Payment?
){
    fun calculateTotalOrderPrice(): BigDecimal {
        totalOrder = products.fold(BigDecimal.ZERO){ acc, product ->
            acc + (product.price.multiply(product.quantity.toBigDecimal()))
        }
        return totalOrder
    }

    fun associatePayment(payment: Payment?) {
        this.payment = payment
    }
}