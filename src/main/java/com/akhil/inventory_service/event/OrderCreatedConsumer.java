package com.akhil.inventory_service.event;

import com.akhil.inventory_service.Service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.akhil.common.events.OrderCreatedEvent;
import org.akhil.common.events.OrderItemEvent;
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
    public void handle(OrderCreatedEvent event) {
        log.error("=================Handling Order Creation.==================");
        for (OrderItemEvent item : event.getItems()) {

            try {
                inventoryService.reserveInventory(
                        event.getOrderId(),
                        item.getProductId(),
                        item.getQuantity()
                );

                eventPublisher.publishReserved(
                        new InventoryReservedEvent(
                                event.getOrderId(),
                                item.getProductId(),
                                item.getQuantity(),
                                event.getCreatedAt()
                        )
                );

            } catch (Exception ex) {

                eventPublisher.publishRejected(
                        new InventoryRejectedEvent(
                                event.getOrderId(),
                                ex.getMessage(),
                                event.getCreatedAt(),
                                item.getProductId()
                        )
                );
            }
        }
    }

}
