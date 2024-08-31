package com.kendimerah.marketplace.service;

import com.kendimerah.marketplace.entity.Cart;
import com.kendimerah.marketplace.entity.CartItem;
import com.kendimerah.marketplace.entity.Product;
import com.kendimerah.marketplace.entity.User;
import com.kendimerah.marketplace.repository.CartRepository;
import com.kendimerah.marketplace.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(new Cart(user)));
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
            CartItem newItem = new CartItem(cart, product, quantity);
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
}