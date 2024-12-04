package com.order.service.infrastructure.api.entities

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class ErrorResponseTest {

    @Test
    fun `should create error response with correct attributes`() {
        val errorResponse = ErrorResponse("Error", "Some error occurred")

        assertNotNull(errorResponse)
        assertEquals("Error", errorResponse.message)
        assertEquals("Some error occurred", errorResponse.detail)
    }
}