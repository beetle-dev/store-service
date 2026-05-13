package com.cafe.storeservice.specification;

import com.cafe.storeservice.domain.*;
import com.cafe.storeservice.dto.inventory.InventoryLogSearchDto;
import org.springframework.data.jpa.domain.Specification;

public class InventoryLogSpecification {

    public InventoryLogSpecification (){}

    public static Specification<InventoryLog> search(Long storeId, InventoryLogSearchDto dto) {
        return Specification.allOf(
                storeIdEquals(storeId),
                ingredientEquals(dto.getIngredientName()),
                changeTypeEquals(dto.getChangeType())
        );
    }

    public static Specification<InventoryLog> storeIdEquals(Long storeId) {
        return (root, query, cb) ->
                storeId != null
                        ? cb.equal(root.get("store").get("id"), storeId)
                        : null;
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
