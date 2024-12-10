package com.order.service.infrastructure.exceptions

import com.order.service.infrastructure.api.entities.ErrorResponse


class ResourceInternalServerException(message: String = "An unexpected error occurred", exception: Exception? = null) :
    RuntimeException(message, exception) {

    fun formatter(): ErrorResponse = ErrorResponse(message = message, detail = cause?.cause?.message)

}