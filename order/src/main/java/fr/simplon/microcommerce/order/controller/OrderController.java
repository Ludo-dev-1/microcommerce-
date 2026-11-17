package fr.simplon.microcommerce.order.controller;

import fr.simplon.microcommerce.order.entity.Order;
import fr.simplon.microcommerce.order.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(
            @RequestParam UUID productId,
            @RequestParam Integer quantity) {

        return orderService.createOrder(productId, quantity);
    }
}