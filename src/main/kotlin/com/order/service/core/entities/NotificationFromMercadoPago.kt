package com.order.service.core.entities

data class Notification(
    val type: String,
    val data: Data
)

data class Data(
    val id: String
)