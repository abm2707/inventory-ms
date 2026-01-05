package com.akhil.inventory_service.DTO;

public record ApiErrorResponse(
        String code,
        String message
) {}
