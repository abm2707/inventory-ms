package com.akhil.inventory_service.event;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class OrderCreatedEvent {

    private UUID orderId;
    private UUID customerId;
    private BigDecimal totalAmount;
    private Date createdAt;
    private String currency;
    private UUID ProductId;
    private int Quantity;
}