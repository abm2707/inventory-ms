package com.akhil.inventory_service.event;

import org.akhil.common.events.StockRejectedEvent;
import org.akhil.common.events.StockReservedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventPublisher {

    private static final String INVENTORY_RESERVED_TOPIC = "inventory.reserved";
    private static final String INVENTORY_REJECTED_TOPIC = "inventory.rejected";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public InventoryEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishReserved(StockReservedEvent event) {
        kafkaTemplate.send(
                INVENTORY_RESERVED_TOPIC,
                event.getOrderId().toString(),
                event
        );
    }

    public void publishRejected(StockRejectedEvent event) {
        kafkaTemplate.send(
                INVENTORY_REJECTED_TOPIC,
                event.getOrderId().toString(),
                event
        );
    }
}
