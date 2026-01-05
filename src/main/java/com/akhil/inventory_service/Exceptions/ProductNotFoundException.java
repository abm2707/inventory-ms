package com.akhil.inventory_service.Exceptions;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductNotFoundException extends RuntimeException {

    private final UUID productId;

    public ProductNotFoundException(UUID productId) {
        super("Product not found in inventory: " + productId);
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
