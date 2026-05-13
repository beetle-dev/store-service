package com.cafe.storeservice.specification;

import com.cafe.storeservice.domain.inventory.StoreInventory;
import com.cafe.storeservice.domain.store.Store;
import com.cafe.storeservice.dto.inventory.InventorySearchDto;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

@NoArgsConstructor
public class InventorySpecification {

    public static Specification<StoreInventory> search(Store store, InventorySearchDto searchDto) {
        return Specification.allOf(
                storeEquals(store),
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

    private static Specification<StoreInventory> storeEquals(Store store) {
        return (root, query, cb) -> cb.equal(root.get("store"), store);
    }
}
