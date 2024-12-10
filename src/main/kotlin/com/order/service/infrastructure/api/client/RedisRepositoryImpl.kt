package com.order.service.infrastructure.api.client

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisRepositoryImpl(private val redisTemplate: RedisTemplate<String, String>) : RedisRepository {

    override fun save(key: String, value: String) {
        redisTemplate.opsForValue().set(key, value)
    }

    override fun findByKey(key: String): String? {
        return redisTemplate.opsForValue().get(key)
    }
}