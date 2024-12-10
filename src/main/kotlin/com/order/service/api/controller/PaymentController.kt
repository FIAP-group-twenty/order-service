package com.order.service.api.controller;

import com.google.gson.Gson
import com.order.service.api.presenters.PaymentPresenter
import com.order.service.core.entities.Notification
import com.order.service.core.entities.Payment
import com.order.service.core.usecase.GetPaymentStatusUseCase
import com.order.service.core.usecase.UpdatePaymentUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/v1/payment")
@RestController
class PaymentController(
    private val paymentStatusUseCase: GetPaymentStatusUseCase,
    private val updatePaymentUseCase: UpdatePaymentUseCase
) {

    @PutMapping("/webhook")
    fun paymentWebhook(@RequestBody notificationJson: String): ResponseEntity<Void> {
        val notification = Gson().fromJson(notificationJson, Notification::class.java)

        updatePaymentUseCase.execute(notification.data.id.toInt())

        return ResponseEntity.ok().build()
    }

    @GetMapping("/status/{orderId}")
    fun paymentStatus(@PathVariable orderId: Int): ResponseEntity<Payment> {
        return ResponseEntity.ok(PaymentPresenter.formatterPayment(paymentStatusUseCase.execute(orderId)))
    }
}
