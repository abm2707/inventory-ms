package com.akhil.inventory_service.Repository;

import com.akhil.inventory_service.Entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.UUID;

public interface InventoryItemRepository
        extends JpaRepository<InventoryItem, UUID> {

    @Modifying
    @Query("""
    UPDATE InventoryItem i
    SET i.availableQuantity = i.availableQuantity - :quantity
    WHERE i.productId = :productId
      AND i.availableQuantity >= :quantity
""")
    int reserveStock(UUID productId, int quantity);

}