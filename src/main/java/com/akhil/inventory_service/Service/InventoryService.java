package com.akhil.inventory_service.Service;

import com.akhil.inventory_service.Entity.InventoryItem;
import com.akhil.inventory_service.Entity.InventoryReservation;
import com.akhil.inventory_service.Entity.ReservationStatus;
import com.akhil.inventory_service.Exceptions.InsufficientInventoryException;
import com.akhil.inventory_service.Repository.InventoryItemRepository;
import com.akhil.inventory_service.Repository.InventoryReservationRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.akhil.common.events.OrderItemEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InventoryService {

    private final InventoryItemRepository inventoryItemRepository;
    private final InventoryReservationRepository reservationRepository;

    public InventoryService(
            InventoryItemRepository inventoryItemRepository,
            InventoryReservationRepository reservationRepository
    ) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public void reserveAll(UUID orderId, List<OrderItemEvent> items) throws InsufficientInventoryException {

        // 1️⃣ Idempotency guard
        if (reservationRepository.existsByOrderId(orderId)) {
            return;
        }

        // 2️⃣ Reserve inventory atomically
        for (OrderItemEvent item : items) {
            int updated = inventoryItemRepository.reserveStock(
                    item.getProductId(),
                    item.getQuantity()
            );

            if (updated == 0) {
                throw new InsufficientInventoryException(item.getProductId());
            }
        }

        // 3️⃣ Mark order as processed
        reservationRepository.save(
                new InventoryReservation(orderId)
        );
    }

}


