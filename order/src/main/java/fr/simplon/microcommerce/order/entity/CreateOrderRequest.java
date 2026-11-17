package fr.simplon.microcommerce.order.entity;

import java.util.UUID;

public record CreateOrderRequest(
        UUID productId,
        Integer quantity
) {}
