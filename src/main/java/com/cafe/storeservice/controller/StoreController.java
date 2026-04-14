package com.cafe.storeservice.controller;

import com.cafe.storeservice.common.response.CommonResponse;
import com.cafe.storeservice.dto.StoreReqDto;
import com.cafe.storeservice.dto.StoreSearchDto;
import com.cafe.storeservice.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<CommonResponse<?>> getStores(@Valid @ModelAttribute StoreSearchDto searchDto) {
        return ResponseEntity.ok(CommonResponse.ok(storeService.getStores(searchDto)));
    }

    @PostMapping
    public ResponseEntity<CommonResponse<?>> register(@Valid @RequestBody StoreReqDto reqDto) {
        //todo 어드민 전용
        storeService.register(reqDto);
        return ResponseEntity.ok(CommonResponse.ok());
    }
}
