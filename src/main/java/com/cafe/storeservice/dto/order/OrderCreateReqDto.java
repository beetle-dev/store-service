package com.cafe.storeservice.dto.order;

import com.cafe.storeservice.domain.order.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderCreateReqDto {
    @NotNull
    private BigDecimal totalAmount;
    @NotNull
    private PaymentMethod paymentMethod;
    @NotEmpty
    List<OrderItemReqDto> orderItemReqDtos;
}
