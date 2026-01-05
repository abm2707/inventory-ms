package com.akhil.inventory_service.RESTClient;

import com.akhil.inventory_service.Exceptions.InsufficientInventoryException;
import com.akhil.inventory_service.Exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class InventoryClient {

    private final RestClient restClient;

    public InventoryClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://inventory-service")
                .build();
    }

    public void validateAvailability(UUID productId, int quantity) {

        restClient.get()
                .uri("/inventory/{id}/availability?quantity={qty}",
                        productId, quantity)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            if (res.getStatusCode() == HttpStatus.NOT_FOUND) {
                                throw new ProductNotFoundException(productId);
                            }
                            if (res.getStatusCode() == HttpStatus.CONFLICT) {
                                try {
                                    throw new InsufficientInventoryException(productId);
                                } catch (InsufficientInventoryException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            return;
                        })
                .toBodilessEntity();
    }
}

