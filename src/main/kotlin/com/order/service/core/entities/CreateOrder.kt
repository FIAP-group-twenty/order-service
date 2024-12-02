package com.order.service.core.entities

import java.math.BigDecimal

data class CreateOrder(
    var idCustomer: Int? = null,
    var totalOrder: BigDecimal = BigDecimal.ZERO,
    var products: List<OrderProduct>,
){
    fun calculateTotalOrderPrice(): BigDecimal {
        totalOrder = products.fold(BigDecimal.ZERO){ acc, product ->
            acc + (product.price.multiply(product.quantity.toBigDecimal()))
        }
        return totalOrder
    }
}