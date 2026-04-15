package com.cafe.storeservice.controller;

import com.cafe.storeservice.common.response.CommonResponse;
import com.cafe.storeservice.dto.InventoryReqDto;
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

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse<?>> modify(@PathVariable("id") Long id,
                                                    @Valid @RequestBody StoreReqDto reqDto) {
        storeService.modify(id, reqDto);
        return ResponseEntity.ok(CommonResponse.ok());
    }

    @GetMapping("/{id}/inventory")
    public ResponseEntity<CommonResponse<?>> getInventory(@PathVariable("id") Long id) {
        return ResponseEntity.ok(CommonResponse.ok(storeService.getInventory(id)));
    }

    @PostMapping("/{id}/inventory/adjust")
    public ResponseEntity<CommonResponse<?>> adjustInventory(@PathVariable("id") Long storeId,
                                                             @Valid @RequestBody InventoryReqDto reqDto) {
        storeService.adjustInventory(storeId, reqDto);
        return ResponseEntity.ok(CommonResponse.ok());
    }

}
