package com.akhil.inventory_service.Controllers;

import com.akhil.inventory_service.Exceptions.InsufficientInventoryException;
import com.akhil.inventory_service.Services.InventoryQueryService;
import com.akhil.inventory_service.DTO.InventoryAvailabilityResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/inventory")
public class InventoryQueryController {

    private final InventoryQueryService queryService;

    public InventoryQueryController(InventoryQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/{productId}/availability")
    public ResponseEntity<InventoryAvailabilityResponse> checkAvailability(
            @PathVariable UUID productId,
            @RequestParam int quantity) throws InsufficientInventoryException {

        InventoryAvailabilityResponse response =
                queryService.checkAvailability(productId, quantity);

        return ResponseEntity.ok(response);
    }
}

