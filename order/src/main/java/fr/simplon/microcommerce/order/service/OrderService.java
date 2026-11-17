package fr.simplon.microcommerce.order.service;

import fr.simplon.microcommerce.order.client.InventoryClient;
import fr.simplon.microcommerce.order.entity.Order;
import fr.simplon.microcommerce.order.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public OrderService(
            OrderRepository orderRepository,
            InventoryClient inventoryClient) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
    }

    public Order createOrder(UUID productId, Integer quantity) {

        Integer stock = inventoryClient.getStock(productId);

        if (stock < quantity) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Stock insuffisant"
            );
        }

        Order order = new Order();
        order.setProductId(productId);
        order.setQuantity(quantity);

        Order savedOrder = orderRepository.save(order);

        inventoryClient.updateStock(
                productId,
                stock - quantity
        );

        return savedOrder;
    }
}