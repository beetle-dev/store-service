package com.cafe.storeservice.dto;

import com.cafe.storeservice.domain.MenuCategories;
import com.cafe.storeservice.domain.Menus;
import com.cafe.storeservice.domain.Store;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class MenuSpecification {

    public MenuSpecification (){}

    public static Specification<Menus> search(MenuSearchDto dto) {
        return Specification.allOf(
                menuCategoryEquals(dto.getMenuCategories()),
                nameContains(dto.getName()),
                isActiveEquals(dto.getIsActive())
        );
    }

    public static Specification<Menus> menuCategoryEquals(MenuCategories menuCategories) {
        return (root, query, cb) ->
                menuCategories != null
                        ? cb.equal(root.get("menuCategories"), menuCategories)
                        : null;
    }

    public static Specification<Menus> nameContains(String name) {
        return (root, query, cb) ->
                StringUtils.hasText(name)
                        ? cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%")
                        : null;
    }

    private static Specification<Menus> isActiveEquals(Boolean isActive) {
        return (root, query, cb) ->
                isActive != null
                        ? cb.equal(root.get("isActive"), isActive)
                        : null;
    }

}
