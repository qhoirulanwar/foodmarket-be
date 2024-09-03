package com.kendimerah.marketplace.dto;

import com.kendimerah.marketplace.entity.Order;
import com.kendimerah.marketplace.entity.OrderItem;
import com.kendimerah.marketplace.entity.OrderStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private Long userId;
    private String userEmail;
    private List<OrderItemDTO> items;
    private BigDecimal totalAmount;
    private String shippingAddress;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public OrderResponseDTO(Order order) {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.userEmail = order.getUser().getEmail();
        this.items = order.getOrderItems().stream()
                .map(OrderItemDTO::new)
                .collect(Collectors.toList());
        this.totalAmount = order.getTotalAmount();
        this.shippingAddress = order.getShippingAddress();
        this.status = order.getStatus();
        this.createdAt = order.getOrderDate();
    }

    // Getters and setters
    @Getter
    @Setter
    @NoArgsConstructor
    public static class OrderItemDTO {
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal price;
        private String imgUrl;

        public OrderItemDTO(OrderItem orderItem) {
            this.productId = orderItem.getProduct().getId();
            this.productName = orderItem.getProduct().getName();
            this.imgUrl = orderItem.getProduct().getImgUrl();
            this.quantity = orderItem.getQuantity();
            this.price = orderItem.getPrice();
        }

        // Getters and setters
    }
}