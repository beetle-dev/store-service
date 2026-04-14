package com.cafe.storeservice.service;

import com.cafe.storeservice.common.exception.CustomException;
import com.cafe.storeservice.common.response.ErrorCode;
import com.cafe.storeservice.common.response.PageResponse;
import com.cafe.storeservice.domain.MenuCategories;
import com.cafe.storeservice.dto.MenuReqDto;
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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void register(MenuReqDto reqDto) {
         MenuCategories menuCategories = menuCategoryRepository.findByNameContaining(reqDto.getMenuCategory())
                 .orElse(null);

         menuRepository.findByName(reqDto.getName())
                 .ifPresent(menus-> {throw new CustomException(ErrorCode.VALIDATION_FAILED);});

         menuRepository.save(Menus.builder()
                         .menuCategories(menuCategories)
                         .name(reqDto.getName())
                         .description(reqDto.getDescription())
                         .price(reqDto.getPrice())
                         .cost(reqDto.getCost())
                         .imageUrl(reqDto.getImageUrl())
                 .build());
    }

    @Transactional
    public void modified(Long id, MenuReqDto reqDto) {
        Menus menu = menuRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND));

        menu.modified(reqDto);
    }
}
