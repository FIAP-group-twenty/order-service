package com.order.service.infrastructure.persistence.entities

import com.order.service.core.entities.OrderStatus.CANCELED
import com.order.service.core.entities.OrderStatus.FINISHED
import com.order.service.core.entities.OrderStatus.IN_PROGRESS
import com.order.service.core.entities.OrderStatus.PENDING
import com.order.service.core.entities.OrderStatus.STARTED
import com.order.service.core.exceptions.ResourceBusinessException
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import lombok.ToString
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
    @ToString.Exclude
    val orderItens: List<OrderItemEntity>,

    @OneToOne(mappedBy = "order", cascade = [CascadeType.ALL])
    @ToString.Exclude
    var payment: PaymentEntity? = null
) {
    fun formatter(order: OrderEntity): OrderEntity {
        order.orderItens.forEach {
            it.order = order
        }

        order.payment?.order = order

        return order
    }

    fun validateStatus(status: String): OrderEntity {
        return when {
            this.status in listOf(FINISHED.name, CANCELED.name) ->
                throw ResourceBusinessException("Order status cannot be updated because it is in final status")

            this.status == status -> throw ResourceBusinessException("Order status is already $status")
            this.status == PENDING.name && status != STARTED.name ->
                throw ResourceBusinessException("Order status cannot be updated to $status")

            this.status == STARTED.name && status !in listOf(CANCELED.name, IN_PROGRESS.name) ->
                throw ResourceBusinessException("Order status cannot be updated to $status")

            this.status == IN_PROGRESS.name && status !in listOf(CANCELED.name, FINISHED.name) ->
                throw ResourceBusinessException("Order status cannot be updated to $status")

            else -> this
        }
    }

    override fun toString(): String {
        return "OrderEntity(idOrder=$idOrder, orderValue=$orderValue, idCustomer=$idCustomer, status=$status)"
    }
}
