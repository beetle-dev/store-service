package com.cafe.storeservice.controller;

import com.cafe.storeservice.common.response.CommonResponse;
import com.cafe.storeservice.domain.auth.Role;
import com.cafe.storeservice.dto.history.SalesHistorySearchDto;
import com.cafe.storeservice.dto.inventory.InventoryLogSearchDto;
import com.cafe.storeservice.dto.inventory.InventoryReqDto;
import com.cafe.storeservice.dto.inventory.InventorySearchDto;
import com.cafe.storeservice.dto.order.OrderCreateReqDto;
import com.cafe.storeservice.dto.order.OrderSearchDto;
import com.cafe.storeservice.dto.order.OrderUpdateReqDto;
import com.cafe.storeservice.dto.store.StoreCreateReqDto;
import com.cafe.storeservice.dto.store.ModifyStoreReqDto;
import com.cafe.storeservice.dto.store.StoreSearchDto;
import com.cafe.storeservice.service.InventoryService;
import com.cafe.storeservice.service.OrderService;
import com.cafe.storeservice.service.SalesQueryService;
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
    private final InventoryService inventoryService;
    private final OrderService orderService;
    private final SalesQueryService salesQueryService;

    @GetMapping
    public ResponseEntity<CommonResponse<?>> getStores(@Valid @ModelAttribute StoreSearchDto searchDto) {
        return ResponseEntity.ok(CommonResponse.ok(storeService.getStores(searchDto)));
    }

    @PostMapping
    public ResponseEntity<CommonResponse<?>> register(@Valid @RequestBody StoreCreateReqDto reqDto,
                                                      @RequestHeader("X-User-Role") Role role) {
        storeService.register(reqDto, role);
        return ResponseEntity.ok(CommonResponse.ok());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse<?>> modify(@PathVariable("id") Long id,
                                                    @Valid @RequestBody ModifyStoreReqDto reqDto) {
        storeService.modify(id, reqDto);
        return ResponseEntity.ok(CommonResponse.ok());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<?>> delete(@PathVariable("id") Long id,
                                                    @RequestHeader("X-User-Role") Role role) {
        storeService.delete(id, role);
        return ResponseEntity.ok(CommonResponse.ok());
    }

    @GetMapping("/{id}/inventory")
    public ResponseEntity<CommonResponse<?>> getInventory(@PathVariable("id") Long id,
                                                          @ModelAttribute InventorySearchDto searchDto) {
        return ResponseEntity.ok(CommonResponse.ok(inventoryService.getInventory(id, searchDto)));
    }

    @PostMapping("/{id}/inventory/adjust")
    public ResponseEntity<CommonResponse<?>> adjustInventory(@PathVariable("id") Long storeId,
                                                             @Valid @RequestBody InventoryReqDto reqDto,
                                                             @RequestHeader("X-User-Id") String uuid) {
        inventoryService.adjustInventory(storeId, reqDto, uuid);
        return ResponseEntity.ok(CommonResponse.ok());
    }

    @GetMapping("/{id}/inventory/logs")
    public ResponseEntity<CommonResponse<?>> getInventoryLogs(@PathVariable("id")Long storeId,
                                                              @ModelAttribute InventoryLogSearchDto searchDto) {
        return ResponseEntity.ok(CommonResponse.ok(inventoryService.getInventoryLogs(storeId, searchDto)));
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<CommonResponse<?>> registerOrder(@PathVariable("id") Long storeId,
                                                           @Valid @RequestBody OrderCreateReqDto orderCreateReqDto) {
        orderService.registerOrder(storeId, orderCreateReqDto);
        return ResponseEntity.ok(CommonResponse.ok());
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<CommonResponse<?>> getOrders(@PathVariable("id") Long storeId,
                                                       @ModelAttribute OrderSearchDto searchDto) {
        return ResponseEntity.ok(CommonResponse.ok(orderService.getOrders(storeId, searchDto)));
    }

    @PatchMapping("/{id}/orders/{orderId}/cancel")
    public ResponseEntity<CommonResponse<?>> updateOrder(@PathVariable("id") Long storeId,
                                                         @PathVariable("orderId") Long orderId,
                                                         @Valid @RequestBody OrderUpdateReqDto reqDto) {
        orderService.updateOrder(storeId, orderId, reqDto);

        return ResponseEntity.ok(CommonResponse.ok());
    }

    @GetMapping("/{id}/sales/daily")
    public ResponseEntity<CommonResponse<?>> getSalesDailyHistory(@PathVariable("id") Long id,
                                                                  @ModelAttribute SalesHistorySearchDto searchDto) {
        return ResponseEntity.ok(CommonResponse.ok(salesQueryService.getSalesDailyHistory(id, searchDto)));
    }

    @GetMapping("/{id}/sales/hourly")
    public ResponseEntity<CommonResponse<?>> getSalesHourlyHistory(@PathVariable("id") Long id,
                                                                  @ModelAttribute SalesHistorySearchDto searchDto) {
        return ResponseEntity.ok(CommonResponse.ok(salesQueryService.getSalesHourlyHistory(id, searchDto)));
    }
}
