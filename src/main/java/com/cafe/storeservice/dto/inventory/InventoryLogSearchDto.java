package com.cafe.storeservice.dto.inventory;

import com.cafe.storeservice.domain.inventory.ChangeType;
import com.cafe.storeservice.dto.SearchDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryLogSearchDto extends SearchDto {
    private String ingredientName;
    private ChangeType changeType;
}
