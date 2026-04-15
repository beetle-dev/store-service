package com.cafe.storeservice.specification;

import com.cafe.storeservice.domain.MenuCategory;
import com.cafe.storeservice.domain.Menu;
import com.cafe.storeservice.dto.MenuSearchDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class MenuSpecification {

    public MenuSpecification (){}

    public static Specification<Menu> search(MenuSearchDto dto) {
        return Specification.allOf(
                menuCategoryEquals(dto.getMenuCategoryEntity()),
                nameContains(dto.getName()),
                isActiveEquals(dto.getIsActive())
        );
    }

    public static Specification<Menu> menuCategoryEquals(MenuCategory menuCategory) {
        return (root, query, cb) ->
                menuCategory != null
                        ? cb.equal(root.get("menuCategory"), menuCategory)
                        : null;
    }

    public static Specification<Menu> nameContains(String name) {
        return (root, query, cb) ->
                StringUtils.hasText(name)
                        ? cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%")
                        : null;
    }

    private static Specification<Menu> isActiveEquals(Boolean isActive) {
        return (root, query, cb) ->
                isActive != null
                        ? cb.equal(root.get("isActive"), isActive)
                        : null;
    }

}
