package com.akhil.inventory_service.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "inventory_reservation",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"order_id", "product_id"})
        }
)
@Getter
@NoArgsConstructor
public class InventoryReservation {

    @Id
    @Column(name = "reservation_id", nullable = false)
    private UUID reservationId;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public InventoryReservation(UUID orderId, UUID productId, int quantity) {
        this.reservationId = UUID.randomUUID();
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = ReservationStatus.RESERVED;
        this.createdAt = Instant.now();
    }
}
