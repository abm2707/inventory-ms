package com.akhil.inventory_service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

public class InsufficientInventoryException extends Throwable {
    private final UUID productId;

    public InsufficientInventoryException(UUID productId) {
        super("Insufficient inventory for product: " + productId);
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
