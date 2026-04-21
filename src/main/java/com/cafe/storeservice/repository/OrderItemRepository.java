package com.cafe.storeservice.repository;

import com.cafe.storeservice.domain.Order;
import com.cafe.storeservice.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrder(Order order);
}
