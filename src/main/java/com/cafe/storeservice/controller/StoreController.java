package com.cafe.storeservice.controller;

import com.cafe.storeservice.common.response.CommonResponse;
import com.cafe.storeservice.dto.inventory.InventoryLogSearchDto;
import com.cafe.storeservice.dto.inventory.InventoryReqDto;
import com.cafe.storeservice.dto.inventory.InventorySearchDto;
import com.cafe.storeservice.dto.order.OrderCreateReqDto;
import com.cafe.storeservice.dto.order.OrderSearchDto;
import com.cafe.storeservice.dto.order.OrderUpdateReqDto;
import com.cafe.storeservice.service.InventoryService;
import com.cafe.storeservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final InventoryService inventoryService;
    private final OrderService orderService;

    @GetMapping("/inventory")
    public ResponseEntity<CommonResponse<?>> getInventory(@ModelAttribute InventorySearchDto searchDto) {
        return ResponseEntity.ok(CommonResponse.ok(inventoryService.getInventory(searchDto)));
    }

    @PostMapping("/inventory/adjust")
    public ResponseEntity<CommonResponse<?>> adjustInventory(@Valid @RequestBody InventoryReqDto reqDto,
                                                             @RequestHeader("X-User-Id") String uuid) {
        inventoryService.adjustInventory(reqDto, uuid);
        return ResponseEntity.ok(CommonResponse.ok());
    }

    @GetMapping("/inventory/logs")
    public ResponseEntity<CommonResponse<?>> getInventoryLogs(@ModelAttribute InventoryLogSearchDto searchDto) {
        return ResponseEntity.ok(CommonResponse.ok(inventoryService.getInventoryLogs(searchDto)));
    }

    @PostMapping("/orders")
    public ResponseEntity<CommonResponse<?>> registerOrder(@Valid @RequestBody OrderCreateReqDto orderCreateReqDto) {
        orderService.registerOrder(orderCreateReqDto);
        return ResponseEntity.ok(CommonResponse.ok());
    }

    @GetMapping("/orders")
    public ResponseEntity<CommonResponse<?>> getOrders(@ModelAttribute OrderSearchDto searchDto) {
        return ResponseEntity.ok(CommonResponse.ok(orderService.getOrders(searchDto)));
    }

    @PatchMapping("/orders/{orderId}/cancel")
    public ResponseEntity<CommonResponse<?>> updateOrder(@PathVariable("orderId") Long orderId,
                                                         @Valid @RequestBody OrderUpdateReqDto reqDto) {
        orderService.updateOrder(orderId, reqDto);

        return ResponseEntity.ok(CommonResponse.ok());
    }
}
