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

    @Version
    private long version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public InventoryItem(UUID productId, int totalQuantity) {
        this.productId = productId;
        this.totalQuantity = totalQuantity;
        this.createdAt = Instant.now();
    }

    public void updateQuantity(int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.totalQuantity = newQuantity;
        this.updatedAt = Instant.now();
    }

    public void increase(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Increase quantity must be positive");
        }
        this.totalQuantity += quantity;
        this.updatedAt = Instant.now();
        validateState();
    }

    public void decrease(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Decrease quantity must be positive");
        }
        if (this.totalQuantity < quantity) {
            throw new IllegalStateException("Insufficient inventory");
        }
        this.totalQuantity -= quantity;
        this.updatedAt = Instant.now();
        validateState();
    }

    public boolean canFulfill(int requestedQuantity) {
        return requestedQuantity > 0 && this.totalQuantity >= requestedQuantity;
    }

    private void validateState() {
        if (this.totalQuantity < 0) {
            throw new IllegalStateException("Inventory quantity cannot be negative");
        }
    }
}
