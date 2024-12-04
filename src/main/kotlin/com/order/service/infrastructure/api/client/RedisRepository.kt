package com.order.service.infrastructure.api.client

interface RedisRepository {
    fun save(key: String, value: String)
    fun findByKey(key: String): String?
}