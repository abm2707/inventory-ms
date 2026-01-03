package com.akhil.inventory_service.event;

import lombok.Data;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Data
public class InventoryReservedEvent {

    private UUID orderId;
    private UUID productId;
    private int quantity;
    private Date occurredAt;

    public InventoryReservedEvent() {}

    public InventoryReservedEvent(
            UUID orderId,
            UUID productId,
            int quantity,
            Date occurredAt
    ) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.occurredAt = occurredAt;
    }
}
