package com.akhil.inventory_service.event;

import com.akhil.inventory_service.Exceptions.InsufficientInventoryException;
import com.akhil.inventory_service.Service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.akhil.common.events.OrderCreatedEvent;
import org.akhil.common.events.StockRejectedEvent;
import org.akhil.common.events.StockReservedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
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
            groupId = "inventory-service-group-v2",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handle(OrderCreatedEvent event) throws InsufficientInventoryException {

        log.error("Handling OrderCreatedEvent for order {}", event.getOrderId());

        try {
            // 1️⃣ Reserve all items atomically
            inventoryService.reserveAll(
                    event.getOrderId(),
                    event.getItems()
            );

            // 2️⃣ Publish ONE success event
            eventPublisher.publishReserved(
                    new StockReservedEvent(event.getOrderId())
            );

        } catch (InsufficientInventoryException ex) {

            // 3️⃣ Publish ONE rejection event
            eventPublisher.publishRejected(
                    new StockRejectedEvent(
                            event.getOrderId(),
                            "INSUFFICIENT_INVENTORY"
                    )
            );

        } catch (Exception ex) {

            // 4️⃣ Technical failure → retryable
            throw ex; // let Kafka retry
        }
    }
}
