package com.cafe.storeservice.specification;

import com.cafe.storeservice.domain.inventory.ChangeType;
import com.cafe.storeservice.domain.inventory.InventoryLog;
import com.cafe.storeservice.dto.inventory.InventoryLogSearchDto;
import org.springframework.data.jpa.domain.Specification;

public class InventoryLogSpecification {

    public InventoryLogSpecification (){}

    public static Specification<InventoryLog> search(InventoryLogSearchDto dto) {
        return Specification.allOf(
                ingredientEquals(dto.getIngredientName()),
                changeTypeEquals(dto.getChangeType())
        );
    }

    public static Specification<InventoryLog> ingredientEquals(String ingredientName) {
        return (root, query, cb) ->
                ingredientName != null
                        ? cb.like(root.get("ingredient").get("name"), "%" + ingredientName + "%")
                        : null;
    }

    public static Specification<InventoryLog> changeTypeEquals(ChangeType changeType) {
        return (root, query, cb) ->
                changeType != null
                        ? cb.equal(root.get("changeType"), changeType)
                        : null;
    }
}
