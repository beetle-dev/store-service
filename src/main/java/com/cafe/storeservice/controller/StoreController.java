package com.cafe.storeservice.controller;

import com.cafe.storeservice.common.response.CommonResponse;
import com.cafe.storeservice.dto.history.SalesHistorySearchDto;
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

    @GetMapping("/{id}/inventory")
    public ResponseEntity<CommonResponse<?>> getInventory(@PathVariable("id") Long id,
                                                          @ModelAttribute InventorySearchDto searchDto) {
        return ResponseEntity.ok(CommonResponse.ok(inventoryService.getInventory(id, searchDto)));
    }

    @PostMapping("/{id}/inventory/adjust")
    public ResponseEntity<CommonResponse<?>> adjustInventory(@Valid @RequestBody InventoryReqDto reqDto,
                                                             @RequestHeader("X-User-Id") String uuid) {
        inventoryService.adjustInventory(reqDto, uuid);
        return ResponseEntity.ok(CommonResponse.ok());
    }

    @GetMapping("/{id}/inventory/logs")
    public ResponseEntity<CommonResponse<?>> getInventoryLogs(@ModelAttribute InventoryLogSearchDto searchDto) {
        return ResponseEntity.ok(CommonResponse.ok(inventoryService.getInventoryLogs(searchDto)));
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<CommonResponse<?>> registerOrder(@Valid @RequestBody OrderCreateReqDto orderCreateReqDto) {
        orderService.registerOrder(orderCreateReqDto);
        return ResponseEntity.ok(CommonResponse.ok());
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<CommonResponse<?>> getOrders(@ModelAttribute OrderSearchDto searchDto) {
        return ResponseEntity.ok(CommonResponse.ok(orderService.getOrders(searchDto)));
    }

    @PatchMapping("/{id}/orders/{orderId}/cancel")
    public ResponseEntity<CommonResponse<?>> updateOrder(@PathVariable("orderId") Long orderId,
                                                         @Valid @RequestBody OrderUpdateReqDto reqDto) {
        orderService.updateOrder(orderId, reqDto);

        return ResponseEntity.ok(CommonResponse.ok());
    }

//    @GetMapping("/{id}/sales/daily")
//    public ResponseEntity<CommonResponse<?>> getSalesDailyHistory(@PathVariable("id") Long id,
//                                                                  @ModelAttribute SalesHistorySearchDto searchDto) {
//        return ResponseEntity.ok(CommonResponse.ok(salesQueryService.getSalesDailyHistory(id, searchDto)));
//    }
//
//    @GetMapping("/{id}/sales/hourly")
//    public ResponseEntity<CommonResponse<?>> getSalesHourlyHistory(@PathVariable("id") Long id,
//                                                                  @ModelAttribute SalesHistorySearchDto searchDto) {
//        return ResponseEntity.ok(CommonResponse.ok(salesQueryService.getSalesHourlyHistory(id, searchDto)));
//    }
}
