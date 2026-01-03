package com.akhil.inventory_service.Service;

import com.akhil.inventory_service.Entity.InventoryItem;
import com.akhil.inventory_service.Entity.InventoryReservation;
import com.akhil.inventory_service.Repository.InventoryItemRepository;
import com.akhil.inventory_service.Repository.InventoryReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
            return; // already processed
        }

        // 2️⃣ Load inventory
        InventoryItem item = itemRepository.findById(productId)
                .orElseThrow(() ->
                        new IllegalStateException("Inventory not found for product " + productId)
                );

        // 3️⃣ Reserve stock (Entity logic)
        item.reserve(quantity);

        // 4️⃣ Persist reservation
        InventoryReservation reservation =
                new InventoryReservation(orderId, productId, quantity);

        reservationRepository.save(reservation);
        itemRepository.save(item);
    }
}
