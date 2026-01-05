package com.akhil.inventory_service.Service;

import lombok.extern.slf4j.Slf4j;
import org.akhil.common.events.OrderCreatedEvent;
import com.akhil.inventory_service.Entity.*;
import com.akhil.inventory_service.Entity.InventoryReservation;
import com.akhil.inventory_service.Repository.InventoryItemRepository;
import com.akhil.inventory_service.Repository.InventoryReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static java.lang.Long.sum;

@Slf4j
@Service
public class InventoryService {

    private final InventoryItemRepository itemRepository;
    private final InventoryReservationRepository reservationRepository;

    public InventoryService(
            InventoryItemRepository itemRepository,
            InventoryReservationRepository reservationRepository
    ) {
        this.itemRepository = itemRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public void reserveInventory(UUID orderId, UUID productId, int quantity) {

        // 1️⃣ Idempotency check
        if (reservationRepository.existsByOrderIdAndProductId(orderId, productId)) {
            return;
        }

        // 2️⃣ Load inventory item
        InventoryItem item = itemRepository.findById(productId)
                .orElseThrow(() ->
                        new IllegalStateException("Inventory not found for product " + productId)
                );

        // 3️⃣ Calculate already reserved quantity
        int alreadyReserved = reservationRepository.sumReservedQuantity(productId, ReservationStatus.RESERVED);
        log.info("Quantity Reserved for productId"+productId+ "is:==>"+alreadyReserved);

        // 4️⃣ Availability check.
        int available = item.getTotalQuantity() - alreadyReserved;
        log.info("Quantity Available for productId"+productId+ "is:==>"+available);

        if (available < quantity) {
            throw new IllegalStateException("Insufficient inventory for product " + productId);
        }

        // 5️⃣ Create reservation (this IS the new reserve)
        InventoryReservation reservation = new InventoryReservation(orderId, productId, quantity);

        reservationRepository.save(reservation);


    }

}
