package com.cafe.storeservice.dto.inventory;

import com.cafe.storeservice.domain.inventory.StoreInventory;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResDto {
    private Long id;
    private Long ingredientId;
    private String ingredientName;
    private String ingredientUnit;
    private BigDecimal currentStock;
    private BigDecimal minStock;
    private LocalDateTime updatedAt;
    private Boolean isLow;

    public static InventoryResDto from(StoreInventory storeInventory) {
        return InventoryResDto.builder()
                .id(storeInventory.getId())
                .ingredientId(storeInventory.getIngredient().getId())
                .ingredientName(storeInventory.getIngredient().getName())
                .ingredientUnit(storeInventory.getIngredient().getUnit())
                .currentStock(storeInventory.getCurrentStock())
                .minStock(storeInventory.getMinStock())
                .updatedAt(storeInventory.getUpdatedAt())
                .isLow(storeInventory.getCurrentStock()
                        .compareTo(storeInventory.getMinStock()) < 0)
                .build();
    }
}
