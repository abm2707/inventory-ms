package com.akhil.inventory_service.Services;

import com.akhil.inventory_service.DTO.InventoryAvailabilityResponse;
import com.akhil.inventory_service.Entity.InventoryItem;
import com.akhil.inventory_service.Entity.ReservationStatus;
import com.akhil.inventory_service.Exceptions.InsufficientInventoryException;
import com.akhil.inventory_service.Exceptions.ProductNotFoundException;
import com.akhil.inventory_service.Repository.InventoryItemRepository;
import com.akhil.inventory_service.Repository.InventoryReservationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InventoryQueryService {

    private final InventoryItemRepository itemRepository;
    private final InventoryReservationRepository reservationRepository;

    public InventoryQueryService(
            InventoryItemRepository itemRepository,
            InventoryReservationRepository reservationRepository) {
        this.itemRepository = itemRepository;
        this.reservationRepository = reservationRepository;
    }

    /*public InventoryAvailabilityResponse checkAvailability(UUID productId, int quantity) throws InsufficientInventoryException {

        InventoryItem item = itemRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException(productId));

        int reservedQty = reservationRepository
                .sumReservedQuantity(productId, ReservationStatus.RESERVED);

        int availableQty = item.getTotalQuantity() - reservedQty;

        if (availableQty < quantity) {
            throw new InsufficientInventoryException(productId);
        }

        return new InventoryAvailabilityResponse(true);
    } */
}

