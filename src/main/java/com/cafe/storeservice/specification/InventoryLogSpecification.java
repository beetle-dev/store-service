package com.cafe.storeservice.specification;

import com.cafe.storeservice.domain.*;
import com.cafe.storeservice.dto.InventoryLogSearchDto;
import org.springframework.data.jpa.domain.Specification;

public class InventoryLogSpecification {

    public InventoryLogSpecification (){}

    public static Specification<InventoryLog> search(InventoryLogSearchDto dto) {
        return Specification.allOf(
                storeIdEquals(dto.getStoreId()),
                ingredientEquals(dto.getIngredientId()),
                changeTypeEquals(dto.getChangeType()),
                performedByEquals(dto.getPerformedBy())
        );
    }

    public static Specification<InventoryLog> storeIdEquals(Long storeId) {
        return (root, query, cb) ->
                storeId != null
                        ? cb.equal(root.get("store").get("id"), storeId)
                        : null;
    }

    public static Specification<InventoryLog> ingredientEquals(Long ingredientId) {
        return (root, query, cb) ->
                ingredientId != null
                        ? cb.equal(root.get("ingredient").get("id"), ingredientId)
                        : null;
    }

    public static Specification<InventoryLog> changeTypeEquals(ChangeType changeType) {
        return (root, query, cb) ->
                changeType != null
                        ? cb.equal(root.get("changeType"), changeType)
                        : null;
    }

    private static Specification<InventoryLog> performedByEquals(Long performedBy) {
        return (root, query, cb) ->
                performedBy != null
                        ? cb.equal(root.get("performedBy"), performedBy)
                        : null;
    }
}
