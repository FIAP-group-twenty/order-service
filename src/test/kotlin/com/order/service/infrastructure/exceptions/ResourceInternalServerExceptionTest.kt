package com.order.service.infrastructure.exceptions

import com.order.service.infrastructure.exceptions.ResourceInternalServerException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ResourceInternalServerExceptionTest {

    @Test
    fun `should throw ResourceInternalServerException with default message`() {
        val exception = assertThrows<ResourceInternalServerException> {
            throw ResourceInternalServerException()
        }
        assertEquals("An unexpected error occurred", exception.message)
    }

    @Test
    fun `should throw ResourceInternalServerException with correct message`() {
        val exception = assertThrows<ResourceInternalServerException> {
            throw ResourceInternalServerException("Resource internal serve")
        }
        assertEquals("Resource internal serve", exception.message)
    }

    @Test
    fun `should throw ResourceInternalServerException with correct message and exception`() {
        val myException = Exception("Testing an ResourceInternalServerException")
        val exception = assertThrows<ResourceInternalServerException> {
            throw ResourceInternalServerException("Resource internal server", myException)
        }
        assertEquals("Resource internal server", exception.message)
        assertEquals(myException, exception.cause)
        assertEquals(myException.message, exception.cause?.message)
    }

    @Test
    fun `should format the error correctly when there is no cause`() {
        val message = "Erro interno"
        val exception = ResourceInternalServerException(message)

        val result = exception.formatter()

        assertEquals(message, result.message)
        assertNull(result.detail)
    }


    @Test
    fun `must format the error correctly when there is a cause with cause`() {
        val message = "Erro ao processar"
        val cause = Exception("Erro na primeira causa", Exception("Detalhes da causa interna"))
        val exception = ResourceInternalServerException(message, cause)

        val result = exception.formatter()

        assertEquals(message, result.message)
        assertEquals("Detalhes da causa interna", result.detail)  // 'detail' deve ser a mensagem da causa da causa
    }

    @Test
    fun `must return null for 'detail' when the internal cause has no message`() {
        val message = "Erro genérico"
        val cause = Exception()
        val exception = ResourceInternalServerException(message, cause)

        val result = exception.formatter()

        assertEquals(message, result.message)
        assertNull(result.detail)
    }

    @Test
    fun `must format the error correctly when the cause has no message and the cause of the cause has`() {
        val message = "Erro genérico"
        val cause = Exception(null, Exception("Detalhes da causa interna"))
        val exception = ResourceInternalServerException(message, cause)

        val result = exception.formatter()

        assertEquals(message, result.message)
        assertEquals("Detalhes da causa interna", result.detail)
    }
}