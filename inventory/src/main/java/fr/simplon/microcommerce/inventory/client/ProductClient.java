package fr.simplon.microcommerce.inventory.client;

import fr.simplon.microcommerce.inventory.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class ProductClient {

    private final RestClient restClient;

    public ProductClient(
            RestClient.Builder builder,
            @Value("${product.base-url}") String baseUrl) {

        this.restClient = builder.baseUrl(baseUrl).build();
    }

    public ProductResponse getProduct(UUID productId) {
        return restClient.get()
                .uri("/products/{id}", productId)
                .retrieve()
                .body(ProductResponse.class);
    }
}