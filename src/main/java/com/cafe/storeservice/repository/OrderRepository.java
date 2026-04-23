package com.cafe.storeservice.repository;

import com.cafe.storeservice.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByStoreId(Long storeId, Pageable pageable);

    List<Order> findAllByCreatedAtBetween(LocalDateTime oneHourAgo, LocalDateTime now);

    long countByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

}