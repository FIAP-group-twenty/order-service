package com.order.service.core.entities

import java.math.BigDecimal

data class OrderProduct(
    var id: Int,
    var quantity: Int,
    var price: BigDecimal = BigDecimal.ZERO,
)