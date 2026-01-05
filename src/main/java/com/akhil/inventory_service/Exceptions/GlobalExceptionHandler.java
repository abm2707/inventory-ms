package com.akhil.inventory_service.Exceptions;

import com.akhil.inventory_service.DTO.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientInventoryException.class)
    public ResponseEntity<ApiErrorResponse> handleInsufficientInventory(
            InsufficientInventoryException ex) {

        ApiErrorResponse error = new ApiErrorResponse(
                "INSUFFICIENT_INVENTORY",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleProductNotFound(
            ProductNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(
                        "PRODUCT_NOT_FOUND",
                        ex.getMessage()
                ));
    }


}
