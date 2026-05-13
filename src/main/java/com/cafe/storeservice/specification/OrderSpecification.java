package com.cafe.storeservice.specification;

import com.cafe.storeservice.domain.order.Order;
import com.cafe.storeservice.domain.store.Store;
import com.cafe.storeservice.dto.order.OrderSearchDto;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class OrderSpecification {
    public static Specification<Order> search(Store store, OrderSearchDto searchDto) {
        return Specification.allOf(
                storeEquals(store),
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

    private static Specification<Order> storeEquals(Store store) {
        return (root, query, cb) ->
                store != null ?
                        cb.equal(root.get("store"), store):
                        null;
    }
}
