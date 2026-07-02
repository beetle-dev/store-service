package com.cafe.storeservice.repository;

import com.cafe.storeservice.domain.order.Order;
import com.cafe.storeservice.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByOrderIdIn(List<Long> orderIds);
}
