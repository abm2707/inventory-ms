package com.akhil.inventory_service.event;

import lombok.Data;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Data
public class InventoryRejectedEvent {

    private UUID orderId;
    private String reason;
    private Date occurredAt;
    private String productId;

    public InventoryRejectedEvent() {}

    public InventoryRejectedEvent(
            UUID orderId,
            String reason,
            Date occurredAt,
            UUID productId
    ) {
        this.orderId = orderId;
        this.reason = reason;
        this.occurredAt = occurredAt;
        this.productId = String.valueOf(productId);
    }
}
