package com.cafe.storeservice.dto;

import com.cafe.storeservice.domain.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderCreateReqDto {
    @NotNull
    private BigDecimal totalAmount;
    @NotNull
    private PaymentMethod paymentMethod;
    @NotEmpty
    List<OrderItemReqDto> orderItemReqDtos;
}
