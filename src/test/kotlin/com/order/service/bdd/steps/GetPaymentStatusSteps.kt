package com.order.service.bdd.steps

import com.mercadopago.resources.payment.Payment
import com.order.service.core.entities.Order
import com.order.service.core.mapper.PaymentMapper
import com.order.service.infrastructure.gateways.OrderGateway
import com.order.service.infrastructure.api.client.PaymentPartnerGateway
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import org.mockito.Mockito.`when`
import org.springframework.boot.test.mock.mockito.MockBean
import java.math.BigDecimal

class GetPaymentStatusSteps {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @MockBean
    private lateinit var orderGateway: OrderGateway

    @MockBean
    private lateinit var paymentGateway: PaymentPartnerGateway

    private var orderId: Int? = null
    private var response: ResponseEntity<Payment>? = null

    @Given("I have an order ID {string}")
    fun iHaveAnOrderId(orderId: String) {
        this.orderId = orderId.toInt()
    }

    @When("I request the payment status")
    fun iRequestThePaymentStatus() {
        val fakeOrder = Order(
            idOrder = orderId,
            payment = PaymentMapper.toEntity(mercadoPagoId = 999, payValue = BigDecimal(500), status = "APPROVED", qrCode = "aaaa"),
            orderValue = BigDecimal(500),
            status = "approved",
            orderItems = listOf()
        )
        `when`(orderGateway.findOrderById(orderId!!)).thenReturn(fakeOrder)

        val fakePayment = Payment()
        `when`(paymentGateway.getPayment(999)).thenReturn(fakePayment)

        val url = "/v1/payment/status/${orderId}"
        response = restTemplate.getForEntity(url, Payment::class.java)
    }

    @Then("I should receive a payment with status {string}")
    fun iShouldReceiveAPaymentWithStatus(expectedStatus: String) {
        assertEquals(200, response?.statusCode?.value())
        val payment = response?.body
        assertEquals(expectedStatus, payment?.status)
    }
}