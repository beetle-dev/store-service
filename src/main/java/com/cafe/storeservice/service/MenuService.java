package com.cafe.storeservice.service;

import com.cafe.storeservice.common.response.PageResponse;
import com.cafe.storeservice.domain.MenuCategories;
import com.cafe.storeservice.dto.MenuSpecification;
import com.cafe.storeservice.domain.Menus;
import com.cafe.storeservice.dto.MenuResDto;
import com.cafe.storeservice.dto.MenuSearchDto;
import com.cafe.storeservice.repository.MenuCategoryRepository;
import com.cafe.storeservice.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuCategoryRepository menuCategoryRepository;

    public PageResponse<MenuResDto> getMenus(MenuSearchDto searchDto) {

        Sort sort = Sort.by(
                Sort.Direction.fromString(searchDto.getSort()),
                searchDto.getDirection()
        );

        Pageable pageable = PageRequest.of(searchDto.getPage(), searchDto.getSize(), sort);

        searchDto.setMenuCategories(menuCategoryRepository.findByNameContaining(searchDto.getMenuCategory()).orElse(null));

        Page<Menus> menus = menuRepository.findAll(MenuSpecification.search(searchDto), pageable);

        return PageResponse.of(menus.map(MenuResDto::from));
    }
}
