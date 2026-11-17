package fr.simplon.microcommerce.order.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class InventoryClient {

    private final RestClient restClient;

    public InventoryClient(
            @Value("${inventory.base-url}") String baseUrl) {

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Integer getStock(UUID productId) {
        return restClient.get()
                .uri("/inventory/{productId}/stock", productId)
                .retrieve()
                .body(Integer.class);
    }

    public void updateStock(UUID productId, Integer newQuantity) {
        restClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/inventory/{productId}")
                        .queryParam("newQuantity", newQuantity)
                        .build(productId))
                .retrieve()
                .toBodilessEntity();
    }
}
