package com.akhil.inventory_service.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemDto {

    private UUID productId;
    private int quantity;

    public UUID getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
