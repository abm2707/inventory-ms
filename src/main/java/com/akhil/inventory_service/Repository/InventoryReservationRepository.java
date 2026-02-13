package com.akhil.inventory_service.Repository;

import com.akhil.inventory_service.Entity.InventoryReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InventoryReservationRepository
        extends JpaRepository<InventoryReservation, UUID> {

    boolean existsByOrderId(UUID orderId);
}
