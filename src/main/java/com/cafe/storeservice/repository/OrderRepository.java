package com.cafe.storeservice.repository;

import com.cafe.storeservice.domain.Order;
import com.cafe.storeservice.domain.SalesStatsDaily;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    long countByOrderedAtBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1);

    Page<Order> findAllByStoreId(Long storeId, Pageable pageable);

    List<Order> findAllAtBetween(LocalDateTime oneHourAgo, LocalDateTime now);

    Page<SalesStatsDaily> findByStoreIdAndCreatedAtBetween(Long id, LocalDateTime from, LocalDateTime to, Pageable pageable);
}
