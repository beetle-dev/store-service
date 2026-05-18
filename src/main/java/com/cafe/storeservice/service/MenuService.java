package com.cafe.storeservice.service;

import com.cafe.storeservice.aws.service.S3Service;
import com.cafe.storeservice.common.exception.CustomException;
import com.cafe.storeservice.common.exception.ErrorCode;
import com.cafe.storeservice.common.response.PageResponse;
import com.cafe.storeservice.domain.menu.MenuCategory;
import com.cafe.storeservice.dto.*;
import com.cafe.storeservice.dto.menu.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import com.cafe.storeservice.domain.menu.Menu;
import com.cafe.storeservice.repository.MenuCategoryRepository;
import com.cafe.storeservice.repository.MenuRepository;
import com.cafe.storeservice.specification.MenuSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    @Value("${cloud.aws.directory.menu}")
    private String menuDirectory;

    private final MenuRepository menuRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    @Cacheable(value = "menu:list", key = "#searchDto.toString()")
    public PageResponse<MenuResDto> getMenus(MenuSearchDto searchDto) {
        Page<Menu> menus = menuRepository.findAll(MenuSpecification.search(searchDto), SearchDto.toPageable(searchDto));

        return PageResponse.of(menus.map(menu -> {
            String imageUrl = menu.getImageUrl() != null
                    ? s3Service.generatePresignedDownloadUrl(menu.getImageUrl())
                    : null;
            return MenuResDto.from(menu, imageUrl);
        }));
    }

    public void register(MenuCreateReqDto reqDto) {
        String key = reqDto.getImage() != null?
                s3Service.upload(reqDto.getImage(), menuDirectory):
                null;

        try {
            saveMenu(reqDto, key);
        } catch (Exception e) {
            if (key != null) s3Service.delete(key);
            throw new CustomException(ErrorCode.REGISTER_FAIL);
        }
    }

    @CacheEvict(value = "menu:list", allEntries = true)
    public void saveMenu(MenuCreateReqDto reqDto, String key) {

        menuRepository.findByName(reqDto.getName())
                .ifPresent(menus-> {throw new CustomException(ErrorCode.DUPLICATE_MENU_NAME);});

        MenuCategory menuCategory = menuCategoryRepository.findById(reqDto.getMenuCategoryId())
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND));

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
    @CacheEvict(value = "menu:list", allEntries = true)
    public void modified(Long id, MenuModifyReqDto reqDto) {
        Long menuCategoryId = reqDto.getMenuCategoryId();
        MenuCategory menuCategory = null;
        String key = null;

        Menu menu = menuRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND));

        if (menuCategoryId != null) {
            menuCategory = menuCategoryRepository.findById(reqDto.getMenuCategoryId())
                    .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND));
        }

        if (reqDto.getImage() != null) {
            key = s3Service.upload(reqDto.getImage(), menuDirectory);
        }

        menu.modified(reqDto, menuCategory, key);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "menu-category:list")
    public PageResponse<MenuCategoryResDto> getCategories() {
        return PageResponse.of(
                menuCategoryRepository.findAll(PageRequest.of(0, 1000, Sort.by("sortOrder").ascending()))
                        .map(MenuCategoryResDto::from)
        );
    }

    @Transactional
    @CacheEvict(value = "menu-category:list", allEntries = true)
    public void registerCategory(MenuCategoryReqDto reqDto) {

        menuCategoryRepository.findByName(reqDto.getName())
                .ifPresent(MenuCategory -> {throw new CustomException(ErrorCode.DUPLICATE_CATEGORY_NAME);});

        MenuCategory.MenuCategoryBuilder builder = MenuCategory.builder()
                .name(reqDto.getName());
        if (reqDto.getSortOrder() != null) builder.sortOrder(reqDto.getSortOrder());
        if (reqDto.getIsActive() != null) builder.isActive(reqDto.getIsActive());
        menuCategoryRepository.save(builder.build());
    }

    @Transactional(readOnly = true)
    public Menu findById(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<Menu> findAllBy(List<Long> menuIdList) {
        return menuRepository.findAllById(menuIdList);
    }
}
