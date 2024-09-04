package com.kendimerah.marketplace.controller;

import com.kendimerah.marketplace.dto.OrderResponseDTO;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<OrderResponseDTO> createOrder(
            Authentication authentication,
            @RequestParam String shippingAddress,
            @RequestParam BigDecimal shippingCost,
            @RequestParam String shippingName) {
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartService.getOrCreateCart(user);
        Order order = orderService.createOrder(user, cart, shippingAddress, shippingCost, shippingName);
        return ResponseEntity.ok(new OrderResponseDTO(order));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrders(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Order> orders = orderService.getOrdersByUser(user);
        List<OrderResponseDTO> orderDTOs = orders.stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            Authentication authentication,
            @PathVariable Long id) {
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Order order = orderService.getOrderById(id);
        if (order != null && order.getUser().getId().equals(user.getId())) {
            return ResponseEntity.ok(new OrderResponseDTO(order));
        }
        return ResponseEntity.notFound().build();
    }
}