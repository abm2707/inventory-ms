package com.akhil.inventory_service.event;

import com.akhil.inventory_service.Service.InventoryService;
import com.akhil.inventory_service.event.InventoryRejectedEvent;
import com.akhil.inventory_service.event.InventoryReservedEvent;
import com.akhil.inventory_service.event.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class OrderCreatedConsumer {

    private final InventoryService inventoryService;
    private final InventoryEventPublisher eventPublisher;

    public OrderCreatedConsumer(
            InventoryService inventoryService,
            InventoryEventPublisher eventPublisher
    ) {
        this.inventoryService = inventoryService;
        this.eventPublisher = eventPublisher;
    }

    @KafkaListener(
            topics = "order.created",
            groupId = "inventory-service-group"
    )
    public void handle(OrderCreatedEvent event) {

        try {
            inventoryService.reserveInventory(
                    event.getOrderId(),
                    event.getProductId(),
                    event.getQuantity()
            );

            // ✅ SUCCESS → publish inventory.reserved
            eventPublisher.publishReserved(
                    new InventoryReservedEvent(
                            event.getOrderId(),
                            event.getProductId(),
                            event.getQuantity(),
                            event.getCreatedAt()
                    )
            );

        } catch (Exception ex) {

            // ❌ FAILURE → publish inventory.rejected
            eventPublisher.publishRejected(
                    new InventoryRejectedEvent(
                            event.getOrderId(),
                            ex.getMessage(),
                            event.getCreatedAt()
                    )
            );
        }
    }
}
