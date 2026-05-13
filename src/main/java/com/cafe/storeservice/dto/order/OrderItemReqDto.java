package com.cafe.storeservice.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemReqDto {
    @NotNull
    private Long menuId;
    @NotNull
    private Integer quantity;
}
