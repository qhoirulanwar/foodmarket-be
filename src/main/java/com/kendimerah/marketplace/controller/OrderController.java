package com.kendimerah.marketplace.controller;

import com.kendimerah.marketplace.entity.Cart;
import com.kendimerah.marketplace.entity.Order;
import com.kendimerah.marketplace.entity.User;
import com.kendimerah.marketplace.service.CartService;
import com.kendimerah.marketplace.service.OrderService;
import com.kendimerah.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<Order> createOrder(
            Authentication authentication,
            @RequestParam String shippingAddress) {
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartService.getOrCreateCart(user);
        Order order = orderService.createOrder(user, cart, shippingAddress);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Order> orders = orderService.getOrdersByUser(user);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(
            Authentication authentication,
            @PathVariable Long id) {
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Order order = orderService.getOrderById(id);
        if (order != null && order.getUser().getId().equals(user.getId())) {
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.notFound().build();
    }
}