package com.kendimerah.marketplace.repository;

import com.kendimerah.marketplace.entity.Order;
import com.kendimerah.marketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}