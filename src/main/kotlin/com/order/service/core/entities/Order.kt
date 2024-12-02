package com.order.service.core.entities

import com.order.service.infrastructure.persistence.entities.OrderItemEntity
import java.math.BigDecimal
import java.time.LocalDateTime

data class Order(
    val idOrder: Int? = null,
    val orderValue: BigDecimal,
    val idCustomer: Int? = null,
    val creationOrder: LocalDateTime? = LocalDateTime.now(),
    val lastUpdateOrder: LocalDateTime? = LocalDateTime.now(),
    val status: String,
    val orderItems: List<OrderItemEntity>,
)