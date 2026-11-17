package fr.simplon.microcommerce.inventory.service;

import fr.simplon.microcommerce.inventory.client.ProductClient;
import fr.simplon.microcommerce.inventory.dto.InventoryResponse;
import fr.simplon.microcommerce.inventory.entity.Inventory;
import fr.simplon.microcommerce.inventory.dto.ProductResponse;
import fr.simplon.microcommerce.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductClient productClient;

    public InventoryService(InventoryRepository inventoryRepository,  ProductClient productClient) {
        this.inventoryRepository = inventoryRepository;
        this.productClient = productClient;
    }

    public Inventory createInventory(UUID productId, int initialQuantity) {
        Inventory inventory = new Inventory(null, productId, initialQuantity);
        return inventoryRepository.save(inventory);
    }

    public Optional<Integer> getStockByProductId(UUID productId) {
        return inventoryRepository.findByProductId(productId)
                .map(Inventory::getStockQuantity);
    }

    public void updateStock(UUID productId, int newQuantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for Book ID: " + productId));

        inventory.setStockQuantity(newQuantity);

        inventoryRepository.save(inventory);
    }

    public InventoryResponse getInventory(UUID productId) {

        Inventory inventory = inventoryRepository
                .findByProductId(productId)
                .orElseThrow();

        ProductResponse product =
                productClient.getProduct(productId);

        return new InventoryResponse(
                productId,
                product.name(),
                product.category(),
                inventory.getStockQuantity()
        );
    }
}

