package fr.simplon.microcommerce.inventory.dto;
import java.util.UUID;

public record InventoryResponse(
        UUID productId,
        String name,
        String category,
        Integer quantity
) {
}