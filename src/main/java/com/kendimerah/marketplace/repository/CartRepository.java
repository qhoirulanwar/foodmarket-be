package com.kendimerah.marketplace.repository;

import com.kendimerah.marketplace.entity.Cart;
import com.kendimerah.marketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}