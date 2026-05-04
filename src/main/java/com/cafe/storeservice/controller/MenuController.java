package com.cafe.storeservice.controller;

import com.cafe.storeservice.common.response.CommonResponse;
import com.cafe.storeservice.dto.MenuCategoryReqDto;
import com.cafe.storeservice.dto.MenuReqDto;
import com.cafe.storeservice.dto.MenuResDto;
import com.cafe.storeservice.dto.MenuSearchDto;
import com.cafe.storeservice.dto.MenuCategoryResDto;
import com.cafe.storeservice.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public ResponseEntity<CommonResponse<?>> getMenus(@Valid @ModelAttribute MenuSearchDto searchDto) {
        return ResponseEntity.ok(CommonResponse.ok(menuService.getMenus(searchDto)));
    }

    @PostMapping
    public ResponseEntity<CommonResponse<?>> register(@Valid @ModelAttribute MenuReqDto reqDto) {
        menuService.register(reqDto);
        return ResponseEntity.ok(CommonResponse.ok());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse<?>> modified(
            @PathVariable("id") Long id,
            @Valid @RequestBody MenuReqDto reqDto) {
        menuService.modified(id, reqDto);
        return ResponseEntity.ok(CommonResponse.ok());
    }

    @GetMapping("/categories")
    public ResponseEntity<CommonResponse<?>> getCategories() {
        return ResponseEntity.ok(CommonResponse.ok(menuService.getCategories()));
    }

    @PostMapping("/category")
    public ResponseEntity<CommonResponse<?>> registerCategory(@Valid @RequestBody MenuCategoryReqDto reqDto) {
        menuService.registerCategory(reqDto);
        return ResponseEntity.ok(CommonResponse.ok());
    }
}
