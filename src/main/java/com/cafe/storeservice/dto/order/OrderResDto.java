package com.cafe.storeservice.dto.order;

import com.cafe.storeservice.domain.order.Order;
import com.cafe.storeservice.domain.order.PaymentMethod;
import com.cafe.storeservice.domain.order.Status;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderResDto {
    private Long id;
    private String orderNumber;
    private Status status;
    private BigDecimal totalAmount;
    private PaymentMethod paymentMethod;
    private LocalDateTime createdAt;
    private List<OrderItemResDto> orderItemResDtoList;

    public static OrderResDto from(Order order, List<OrderItemResDto> orderItemResDtoList) {
        return OrderResDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .paymentMethod(order.getPaymentMethod())
                .createdAt(order.getCreatedAt())
                .orderItemResDtoList(orderItemResDtoList)
                .build();
    }
}
