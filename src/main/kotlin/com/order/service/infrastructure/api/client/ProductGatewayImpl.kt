package com.order.service.infrastructure.api.client

import com.order.service.core.entities.Product
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class ProductGatewayImpl : ProductGateway {

    private val restTemplate = RestTemplate()

    override fun getProductById(productId: Int): Product {
        val url = ""
        val response = restTemplate.getForEntity(url, Product::class.java)
        return response.body!!
    }
}