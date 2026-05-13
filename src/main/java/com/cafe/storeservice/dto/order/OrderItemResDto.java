package com.cafe.storeservice.dto.order;

import com.cafe.storeservice.domain.order.OrderItem;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OrderItemResDto {
    private Long id;
    private Long orderId;
    private Long menuId;
    private String menuName;
    private BigDecimal unitPrice;
    private Integer quantity;
    private BigDecimal subtotal;

    public static OrderItemResDto from(OrderItem orderItem) {
        return OrderItemResDto.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .menuId(orderItem.getMenu().getId())
                .menuName(orderItem.getMenuName())
                .unitPrice(orderItem.getUnitPrice())
                .quantity(orderItem.getQuantity())
                .subtotal(orderItem.getSubtotal())
                .build();
    }
}
