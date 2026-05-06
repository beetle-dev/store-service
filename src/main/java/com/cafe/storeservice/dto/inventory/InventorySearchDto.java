package com.cafe.storeservice.dto.inventory;

import com.cafe.storeservice.dto.SearchDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InventorySearchDto extends SearchDto {
    private String ingredientName;
    private Boolean low;
}
