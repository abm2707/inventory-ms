package com.akhil.inventory_service.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;


@Entity
@Table(
        name = "inventory_reservation",
        indexes = {
                @Index(name = "idx_reservation_order", columnList = "order_id"),
                @Index(name = "idx_reservation_product", columnList = "product_id")
        }
)

@AllArgsConstructor
@NoArgsConstructor
public class InventoryReservation {

    @Id
    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public InventoryReservation(UUID orderId) {
        this.orderId = orderId;
        this.createdAt = Instant.now();
    }
}

