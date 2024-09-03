package com.kendimerah.marketplace.controller;

import com.kendimerah.marketplace.dto.CartResponseDTO;
import com.kendimerah.marketplace.entity.Cart;
import com.kendimerah.marketplace.entity.CartItem;
import com.kendimerah.marketplace.entity.User;
import com.kendimerah.marketplace.service.CartService;
import com.kendimerah.marketplace.service.ProductService;
import com.kendimerah.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartService.getOrCreateCart(user);
        BigDecimal total = cartService.calculateCartTotal(cart);
        CartResponseDTO response = new CartResponseDTO(cart, total);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addToCart(
            Authentication authentication,
            @RequestParam Long productId,
            @RequestParam int quantity) {
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartService.getOrCreateCart(user);
        return productService.getProductById(productId)
                .map(product -> {
                    CartItem cartItem = cartService.addToCart(cart, product, quantity);
                    return ResponseEntity.ok(cartItem);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeFromCart(
            Authentication authentication,
            @PathVariable Long productId) {
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartService.getOrCreateCart(user);
        cartService.removeFromCart(cart, productId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/clear")
    public ResponseEntity<Void> clearCart(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartService.getOrCreateCart(user);
        cartService.clearCart(cart);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reduce")
    public ResponseEntity<CartItem> reduceQuantity(
            Authentication authentication,
            @RequestParam Long productId,
            @RequestParam int quantity) {
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartService.getOrCreateCart(user);
        CartItem updatedCartItem = cartService.reduceQuantity(cart, productId, quantity);
        return ResponseEntity.ok(updatedCartItem);
    }
}