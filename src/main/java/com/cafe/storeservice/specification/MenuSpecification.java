package com.cafe.storeservice.specification;

import com.cafe.storeservice.domain.Menu;
import com.cafe.storeservice.dto.menu.MenuSearchDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class MenuSpecification {

    public MenuSpecification (){}

    public static Specification<Menu> search(MenuSearchDto dto) {
        return Specification.allOf(
                menuCategoryEquals(dto.getMenuCategoryId()),
                nameContains(dto.getName()),
                isActiveEquals(dto.getIsActive())
        );
    }

    public static Specification<Menu> menuCategoryEquals(Long menuCategoryId) {
        return (root, query, cb) ->
                menuCategoryId != null
                        ? cb.equal(root.get("menuCategory").get("id"), menuCategoryId)
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
