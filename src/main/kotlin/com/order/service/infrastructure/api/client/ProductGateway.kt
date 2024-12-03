package com.order.service.infrastructure.api.client

import com.order.service.core.entities.Product

interface ProductGateway {
    fun getProductById(productId: Int): Product
}