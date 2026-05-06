package com.cafe.storeservice.specification;

import com.cafe.storeservice.domain.inventory.StoreInventory;
import com.cafe.storeservice.dto.inventory.InventorySearchDto;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

@NoArgsConstructor
public class InventorySpecification {

    public static Specification<StoreInventory> search(Long id, InventorySearchDto searchDto) {
        return Specification.allOf(
                storeIdEquals(id),
                ingredientContains(searchDto.getIngredientName()),
                lowIsEquals(searchDto.getLow())
        );
    }

    private static Specification<StoreInventory> lowIsEquals(Boolean low) {
        return (root, query, cb) ->
                low != null
                        ? low ? cb.lessThan(root.get("currentStock"),root.get("minStock"))
                            : cb.greaterThanOrEqualTo(root.get("currentStock"),root.get("minStock"))
                :null;
    }

    private static Specification<StoreInventory> ingredientContains(String ingredientName) {
        return (root, query, cb) ->
                StringUtils.hasText(ingredientName)
                        ? cb.like(root.get("ingredient").get("name"), "%" + ingredientName + "%")
                        : null;
    }

    private static Specification<StoreInventory> storeIdEquals(Long id) {
        return (root, query, cb) -> cb.equal(root.get("store").get("id"), id);
    }
}
