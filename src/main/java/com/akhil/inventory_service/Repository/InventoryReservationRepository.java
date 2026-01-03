package com.akhil.inventory_service.Repository;

import com.akhil.inventory_service.Entity.InventoryReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryReservationRepository
        extends JpaRepository<InventoryReservation, UUID> {

    boolean existsByOrderIdAndProductId(UUID orderId, UUID productId);
}
