package com.cafe.storeservice.dto;

import com.cafe.storeservice.domain.Ingredients;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class StoreInventoryResDto {
    private Long id;
    private Ingredients ingredients;
    private BigDecimal currentStock;
    private BigDecimal minStock;
    private LocalDateTime updatedAt;
}
