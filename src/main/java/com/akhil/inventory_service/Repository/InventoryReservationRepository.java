package com.akhil.inventory_service.Repository;

import com.akhil.inventory_service.Entity.InventoryReservation;
import com.akhil.inventory_service.Entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface InventoryReservationRepository
        extends JpaRepository<InventoryReservation, UUID> {

    boolean existsByOrderIdAndProductId(UUID orderId, UUID productId);

    @Query("""
        SELECT COALESCE(SUM(r.quantity), 0)
        FROM InventoryReservation r
        WHERE r.productId = :productId
          AND r.status = :status
    """)
    int sumReservedQuantity(@Param("productId") UUID productId,
                            @Param("status") ReservationStatus status);
}
