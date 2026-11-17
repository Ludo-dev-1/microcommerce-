package fr.simplon.microcommerce.inventory.controller;
import fr.simplon.microcommerce.inventory.entity.Inventory;
import fr.simplon.microcommerce.inventory.service.InventoryService;

import fr.simplon.microcommerce.inventory.dto.InventoryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private static final Logger logger =
            LoggerFactory.getLogger(InventoryController.class);

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/{productId}")
    public Inventory initializeInventory(
            @PathVariable UUID productId,
            @RequestParam(defaultValue = "0") int initialQuantity) {

        Inventory inventory =
                inventoryService.createInventory(productId, initialQuantity);

        logger.info(
                "Inventory created for product {} with quantity {}",
                productId,
                initialQuantity
        );

        return inventory;
    }

    /**
     * Utilisé pour consulter un inventaire complet.
     * Retourne nom, catégorie et quantité.
     */
    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponse> getInventory(
            @PathVariable UUID productId) {

        return ResponseEntity.ok(
                inventoryService.getInventory(productId)
        );
    }

    /**
     * Utilisé par le microservice Order.
     * Retourne uniquement la quantité disponible.
     */
    @GetMapping("/{productId}/stock")
    public ResponseEntity<Integer> getStock(
            @PathVariable UUID productId) {

        return inventoryService.getStockByProductId(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> updateStock(
            @PathVariable UUID productId,
            @RequestParam int newQuantity) {

        inventoryService.updateStock(productId, newQuantity);

        return ResponseEntity.ok(
                "Stock updated for product ID: "
                        + productId
                        + " with new quantity: "
                        + newQuantity
        );
    }
}
