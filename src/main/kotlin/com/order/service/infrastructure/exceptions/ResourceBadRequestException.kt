package com.order.service.infrastructure.exceptions

import com.order.service.infrastructure.api.entities.ErrorResponse

class ResourceBadRequestException(message: String, exception: Exception? = null) :
    RuntimeException(message, exception) {

    fun formatter(): ErrorResponse = ErrorResponse(message = message, detail = cause?.message)

}