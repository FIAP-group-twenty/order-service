package com.order.service.infrastructure.api.entities

data class ErrorResponse(
    val message: String?,
    val detail: String? = null
)