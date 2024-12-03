package core.exceptions

import br.group.twenty.challenge.core.exceptions.ResourceBusinessException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ResourceBusinessExceptionTest {
    @Test
    fun `should throw ResourceBusinessException with correct message`() {
        val exception = assertThrows<ResourceBusinessException> {
            throw ResourceBusinessException("error to create order")
        }
        Assertions.assertEquals("error to create order", exception.message)
    }
}