package com.order.service.api.controller

import com.order.service.core.entities.CreateOrder
import com.order.service.core.entities.Order
import com.order.service.core.entities.UpdateOrder
import com.order.service.core.usecase.CreateOrderUseCase
import com.order.service.core.usecase.ListOrderUseCase
import com.order.service.core.usecase.UpdateOrderUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/v1/orders")
@RestController
class OrderController(
    private val createOrderUseCase: CreateOrderUseCase,
    private val listOrdersUseCase: ListOrderUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase
) {

    @PostMapping
    fun createOrder(@RequestBody createOrder: CreateOrder): ResponseEntity<Order> {
        return ResponseEntity.status(HttpStatus.CREATED).body(createOrderUseCase.execute(createOrder))
    }

    @GetMapping
    fun listOrders(): ResponseEntity<List<Order>> {
        return ResponseEntity.ok(listOrdersUseCase.execute())
    }

    @PutMapping("/{id}")
    fun updateOrderStatus(@PathVariable id: Int, @RequestBody order: UpdateOrder): ResponseEntity<Order> {
        return ResponseEntity.ok(updateOrderUseCase.execute(id, order))
    }

}