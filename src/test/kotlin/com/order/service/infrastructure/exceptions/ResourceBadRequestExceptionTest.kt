package com.order.service.infrastructure.exceptions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ResourceBadRequestExceptionTest {

    @Test
    fun `should throw ResourceBadRequestException with correct message`() {
        val exception = assertThrows<ResourceBadRequestException> {
            throw ResourceBadRequestException("Resource bad request")
        }
        assertEquals("Resource bad request", exception.message)
    }

    @Test
    fun `should throw ResourceBadRequestException with correct message and exception`() {
        val myException = Exception("Testing an exception")
        val exception = assertThrows<ResourceBadRequestException> {
            throw ResourceBadRequestException("Resource bad request", myException)
        }
        assertEquals("Resource bad request", exception.message)
        assertEquals(myException, exception.cause)
        assertEquals(myException.message, exception.cause?.message)
    }

    @Test
    fun `must correctly format the uncaused error response`() {
        val exception = ResourceBadRequestException("Erro ao processar solicitação")

        val errorResponse = exception.formatter()

        assertEquals("Erro ao processar solicitação", errorResponse.message)
        assertNull(errorResponse.detail)
    }

    @Test
    fun `must correctly format the error response with cause`() {
        val innerException = Exception("Detalhe da exceção interna")
        val exception = ResourceBadRequestException("Erro ao processar solicitação", innerException)

        val errorResponse = exception.formatter()

        assertEquals("Erro ao processar solicitação", errorResponse.message)
        assertEquals("Detalhe da exceção interna", errorResponse.detail)
    }

}