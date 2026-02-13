package com.akhil.inventory_service.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "inventory_item")
@Getter
@NoArgsConstructor
public class InventoryItem {

    @Id
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "total_quantity", nullable = false)
    private int totalQuantity;

    @Column(name = "available_quantity", nullable = false)
    private int availableQuantity;

    @Version
    private long version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public InventoryItem(UUID productId, int totalQuantity) {
        if (totalQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.productId = productId;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = totalQuantity;
        this.createdAt = Instant.now();
    }

    /* ===== Domain behavior ===== */

    public boolean canReserve(int quantity) {
        return quantity > 0 && availableQuantity >= quantity;
    }

    public void reserve(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Reserve quantity must be positive");
        }
        if (availableQuantity < quantity) {
            throw new IllegalStateException("Insufficient inventory");
        }
        this.availableQuantity -= quantity;
        this.updatedAt = Instant.now();
    }

    public void release(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Release quantity must be positive");
        }
        this.availableQuantity += quantity;
        if (this.availableQuantity > this.totalQuantity) {
            this.availableQuantity = this.totalQuantity;
        }
        this.updatedAt = Instant.now();
    }
}
