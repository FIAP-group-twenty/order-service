package com.order.service.core.entities

import java.time.LocalDateTime

data class UpdateOrder(
    val status: String,
    val lastUpdateOrder: LocalDateTime? = LocalDateTime.now(),
)