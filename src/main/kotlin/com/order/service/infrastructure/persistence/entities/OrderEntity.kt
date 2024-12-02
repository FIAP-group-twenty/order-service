package com.order.service.infrastructure.persistence.entities

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "tb_order")
data class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idOrder: Int? = null,
    val orderValue: BigDecimal,

    @NotNull
    val idCustomer: Int? = null,

    val creationOrder: LocalDateTime? = LocalDateTime.now(),
    var lastUpdateOrder: LocalDateTime? = LocalDateTime.now(),
    var status: String,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderItens: List<OrderItemEntity>,

    ) {
    fun formatter(order: OrderEntity): OrderEntity {
        order.orderItens.forEach {
            it.order = order
        }
        return order
    }

    override fun toString(): String {
        return "OrderEntity(idOrder=$idOrder, orderValue=$orderValue, idCustomer=$idCustomer, status=$status)"
    }
}