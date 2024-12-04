package com.order.service.infrastructure.persistence.jpa

import com.order.service.infrastructure.persistence.entities.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentDataSource : JpaRepository<PaymentEntity, Int> {
    fun findByMercadoPagoId(id: Int): PaymentEntity?
}
