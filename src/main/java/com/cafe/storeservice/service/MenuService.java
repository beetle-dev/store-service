package com.cafe.storeservice.service;

import com.cafe.storeservice.aws.service.S3Service;
import com.cafe.storeservice.common.exception.CustomException;
import com.cafe.storeservice.common.exception.ErrorCode;
import com.cafe.storeservice.common.response.PageResponse;
import com.cafe.storeservice.domain.MenuCategory;
import com.cafe.storeservice.dto.*;
import com.cafe.storeservice.domain.Menu;
import com.cafe.storeservice.repository.MenuCategoryRepository;
import com.cafe.storeservice.repository.MenuRepository;
import com.cafe.storeservice.specification.MenuSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {

    @Value("${cloud.aws.directory.menu}")
    private String menuDirectory;

    private final MenuRepository menuRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    public PageResponse<MenuResDto> getMenus(MenuSearchDto searchDto) {
        Page<Menu> menus = menuRepository.findAll(MenuSpecification.search(searchDto), SearchDto.toPageable(searchDto));

        return PageResponse.of(menus.map(menu -> {
            String imageUrl = menu.getImageUrl() != null
                    ? s3Service.generatePresignedDownloadUrl(menu.getImageUrl())
                    : null;
            return MenuResDto.from(menu, imageUrl);
        }));
    }

    @Transactional
    public void register(MenuReqDto reqDto) {
        String key = null;

        menuRepository.findByName(reqDto.getName())
                .ifPresent(menus-> {throw new CustomException(ErrorCode.DUPLICATE_MENU_NAME);});

        MenuCategory menuCategory = menuCategoryRepository.findByNameContaining(reqDto.getMenuCategory())
                 .orElse(null);

        if (reqDto.getImage() != null) {
            key = s3Service.upload(reqDto.getImage(), menuDirectory);
        }

        menuRepository.save(Menu.builder()
                         .menuCategory(menuCategory)
                         .name(reqDto.getName())
                         .description(reqDto.getDescription())
                         .price(reqDto.getPrice())
                         .cost(reqDto.getCost())
                         .imageUrl(key)
                 .build());
    }

    @Transactional
    public void modified(Long id, MenuReqDto reqDto) {
        String key = null;

        Menu menu = menuRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND));

        if (reqDto.getImage() != null) {
            key = s3Service.upload(reqDto.getImage(), menuDirectory);
        }

        menu.modified(reqDto, key);
    }

    public void registerCategory(MenuCategoryReqDto reqDto) {

        menuCategoryRepository.findByName(reqDto.getName())
                .ifPresent(MenuCategory -> {throw new CustomException(ErrorCode.DUPLICATE_MENU_NAME);});

        MenuCategory.MenuCategoryBuilder builder = MenuCategory.builder()
                .name(reqDto.getName());
        if (reqDto.getSortOrder() != null) builder.sortOrder(reqDto.getSortOrder());
        if (reqDto.getIsActive() != null) builder.isActive(reqDto.getIsActive());
        menuCategoryRepository.save(builder.build());
    }
}
