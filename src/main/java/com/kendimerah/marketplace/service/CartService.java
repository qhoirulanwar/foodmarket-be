package com.kendimerah.marketplace.service;

import com.kendimerah.marketplace.entity.Cart;
import com.kendimerah.marketplace.entity.CartItem;
import com.kendimerah.marketplace.entity.Product;
import com.kendimerah.marketplace.entity.User;
import com.kendimerah.marketplace.repository.CartRepository;
import com.kendimerah.marketplace.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    public CartItem addToCart(Cart cart, Product product, int quantity) {
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            return cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);

            cart.addCartItem(newItem);
            return cartItemRepository.save(newItem);
        }
    }

    public void removeFromCart(Cart cart, Long productId) {
        cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
    }

    public void clearCart(Cart cart) {
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    public BigDecimal calculateCartTotal(Cart cart) {
        return cart.getCartItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public CartItem reduceQuantity(Cart cart, Long productId, int quantity) {
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() - quantity;
            if (newQuantity <= 0) {
                removeFromCart(cart, productId);
                return null;
            } else {
                item.setQuantity(newQuantity);
                return cartItemRepository.save(item);
            }
        } else {
            throw new RuntimeException("Produk tidak ditemukan dalam keranjang");
        }
    }
}