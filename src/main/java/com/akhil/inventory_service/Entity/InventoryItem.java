package com.akhil.inventory_service.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "inventory_item")
@Getter
@NoArgsConstructor
public class InventoryItem {

    @Id
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "available_quantity", nullable = false)
    private int availableQuantity;

    @Column(name = "reserved_quantity", nullable = false)
    private int reservedQuantity;

    @Version
    private long version;

    public InventoryItem(UUID productId, int availableQuantity) {
        this.productId = productId;
        this.availableQuantity = availableQuantity;
        this.reservedQuantity = 0;
    }

    public void reserve(int quantity) {
        if (availableQuantity < quantity) {
            throw new IllegalStateException("Insufficient inventory");
        }
        this.availableQuantity -= quantity;
        this.reservedQuantity += quantity;
    }
}
