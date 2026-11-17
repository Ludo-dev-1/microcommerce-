package fr.simplon.microcommerce.inventory.dto;

import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String category
) {
}
