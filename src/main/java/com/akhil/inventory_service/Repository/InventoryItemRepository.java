package com.akhil.inventory_service.Repository;

import com.akhil.inventory_service.Entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryItemRepository
        extends JpaRepository<InventoryItem, UUID> {
}