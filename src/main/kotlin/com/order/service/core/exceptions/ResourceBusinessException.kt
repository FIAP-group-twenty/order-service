package com.order.service.core.exceptions

import com.order.service.infrastructure.api.entities.ErrorResponse


class ResourceBusinessException(message: String) : RuntimeException(message) {

    fun formatter(): ErrorResponse = ErrorResponse(message = message)

}