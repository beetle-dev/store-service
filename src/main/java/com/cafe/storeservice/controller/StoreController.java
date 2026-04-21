package com.cafe.storeservice.controller;

import com.cafe.storeservice.common.response.CommonResponse;
import com.cafe.storeservice.dto.*;
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
    public ResponseEntity<CommonResponse<?>> getInventory(@PathVariable("id") Long id,
                                                          @ModelAttribute SearchDto searchDto) {
        return ResponseEntity.ok(CommonResponse.ok(storeService.getInventory(id, searchDto)));
    }

    @PostMapping("/{id}/inventory/adjust")
    public ResponseEntity<CommonResponse<?>> adjustInventory(@PathVariable("id") Long storeId,
                                                             @Valid @RequestBody InventoryReqDto reqDto) {
        storeService.adjustInventory(storeId, reqDto);
        return ResponseEntity.ok(CommonResponse.ok());
    }

    @GetMapping("/{id}/inventory/logs")
    public ResponseEntity<CommonResponse<?>> getInventoryLogs(@PathVariable("id")Long storeId,
                                                              @ModelAttribute InventoryLogSearchDto searchDto) {
        return ResponseEntity.ok(CommonResponse.ok(storeService.getInventoryLogs(storeId, searchDto)));
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<CommonResponse<?>> registerOrder(@PathVariable("id") Long storeId,
                                                           @Valid @RequestBody OrderCreateReqDto orderCreateReqDto) {
        storeService.registerOrder(storeId, orderCreateReqDto);
        return ResponseEntity.ok(CommonResponse.ok());
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<CommonResponse<?>> getOrders(@PathVariable("id") Long storeId,
                                                       @ModelAttribute SearchDto searchDto) {
        return ResponseEntity.ok(CommonResponse.ok(storeService.getOrders(storeId, searchDto)));
    }

    @PatchMapping("/{id}/orders/{orderId}/cancel")
    public ResponseEntity<CommonResponse<?>> updateOrder(@PathVariable("id") Long storeId,
                                                         @PathVariable("orderId") Long orderId,
                                                         @Valid @RequestBody OrderUpdateReqDto reqDto) {
        storeService.updateOrder(storeId, orderId, reqDto);

        return ResponseEntity.ok(CommonResponse.ok());
    }
}
