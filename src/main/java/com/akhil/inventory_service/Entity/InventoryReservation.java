package com.akhil.inventory_service.Entity;

import jakarta.persistence.*;
import lombok.Getter;
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
@Getter
@NoArgsConstructor
public class InventoryReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reservation_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public InventoryReservation(UUID orderId,
                                UUID productId,
                                int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = ReservationStatus.RESERVED;
        this.createdAt = Instant.now();
    }

    public void release() {
        this.status = ReservationStatus.RELEASED;
    }

    public void confirm() {
        this.status = ReservationStatus.CONFIRMED;
    }
}
