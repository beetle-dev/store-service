package com.cafe.storeservice.specification;

import com.cafe.storeservice.domain.order.Order;
import com.cafe.storeservice.dto.order.OrderSearchDto;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class OrderSpecification {
    public static Specification<Order> search(OrderSearchDto searchDto) {
        return Specification.allOf(
                startDateEqulas(searchDto.getOrderStartDate()),
                endDateEquals(searchDto.getOrderEndDate())
        );
    }

    private static Specification<Order> endDateEquals(LocalDateTime orderEndDate) {
        return (root, query, cb) ->
                orderEndDate != null?
                        cb.lessThanOrEqualTo(root.get("createdAt"), orderEndDate.toLocalDate().atTime(LocalTime.MAX)):
                        null;
    }

    private static Specification<Order> startDateEqulas(LocalDateTime orderStartDate) {
        return (root, query, cb) ->
                orderStartDate != null?
                        cb.greaterThanOrEqualTo(root.get("createdAt"), orderStartDate.toLocalDate().atTime(LocalTime.MIN)):
                        null;
    }
}
