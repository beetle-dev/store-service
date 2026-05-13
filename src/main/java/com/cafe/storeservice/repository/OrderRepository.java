package com.cafe.storeservice.repository;

import com.cafe.storeservice.domain.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findAllByCreatedAtBetween(LocalDateTime oneHourAgo, LocalDateTime now);

    long countByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    Page<Order> findAll(Specification<Order> search, Pageable pageable);

    @Query(value = "SELECT nextval('order_number_seq')", nativeQuery = true)
    Long getNextOrderSequence();
}