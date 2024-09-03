package com.kendimerah.marketplace.dto;

import com.kendimerah.marketplace.entity.Cart;
import com.kendimerah.marketplace.entity.CartItem;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CartResponseDTO {
    private Long id;
    private List<CartItemDTO> items;
    private BigDecimal total;
    private int totalQuantity;

    public CartResponseDTO(Cart cart, BigDecimal total) {
        this.id = cart.getId();
        this.items = cart.getCartItems().stream()
                .map(CartItemDTO::new)
                .collect(Collectors.toList());
        this.total = total;
        this.totalQuantity = calculateTotalQuantity();
    }

    private int calculateTotalQuantity() {
        return items.stream()
                .mapToInt(CartItemDTO::getQuantity)
                .sum();
    }

    @Getter
    @Setter
    public static class CartItemDTO {
        private Long productId;
        private String productName;
        private String productImgUrl;
        private BigDecimal price;
        private int quantity;
        private BigDecimal total;

        public CartItemDTO(CartItem cartItem) {
            this.productId = cartItem.getProduct().getId();
            this.productName = cartItem.getProduct().getName();
            this.productImgUrl = cartItem.getProduct().getImgUrl();
            this.price = cartItem.getProduct().getPrice();
            this.quantity = cartItem.getQuantity();
            this.total = this.price.multiply(BigDecimal.valueOf(this.quantity));
        }
    }
}