package com.cafe.storeservice.dto;

import com.cafe.storeservice.domain.ChangeType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryLogSearchDto extends SearchDto {
    private Long storeId;
    private Long ingredientId;
    private ChangeType changeType;
    private Long performedBy;
}
