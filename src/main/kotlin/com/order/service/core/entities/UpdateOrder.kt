package com.order.service.core.entities

import com.order.service.infrastructure.persistence.entities.PaymentEntity
import java.time.LocalDateTime

data class UpdateOrder(
    val status: String,
    val lastUpdateOrder: LocalDateTime? = LocalDateTime.now(),
    val payment: PaymentEntity? = null
)