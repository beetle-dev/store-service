package com.cafe.storeservice.repository;

import com.cafe.storeservice.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface OrderRepository extends JpaRepository<Order, Long> {
    long countByOrderedAtBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1);

    Page<Order> findAllByStoreId(Long storeId, Pageable pageable);
}
