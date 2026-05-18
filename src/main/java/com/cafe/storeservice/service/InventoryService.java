package com.cafe.storeservice.service;

import com.cafe.storeservice.common.exception.CustomException;
import com.cafe.storeservice.common.exception.ErrorCode;
import com.cafe.storeservice.common.response.PageResponse;
import com.cafe.storeservice.domain.inventory.InventoryLog;
import com.cafe.storeservice.domain.inventory.StoreInventory;
import com.cafe.storeservice.domain.store.Store;
import com.cafe.storeservice.dto.*;
import com.cafe.storeservice.dto.inventory.*;
import com.cafe.storeservice.repository.InventoryLogRepository;
import com.cafe.storeservice.repository.StoreInventoryRepository;
import com.cafe.storeservice.specification.InventoryLogSpecification;
import com.cafe.storeservice.specification.InventorySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final StoreService storeService;

    private final StoreInventoryRepository storeInventoryRepository;
    private final InventoryLogRepository inventoryLogRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "inventory:list", key = "#id + ':' + #searchDto.toString()")
    public PageResponse<InventoryResDto> getInventory(Long id, InventorySearchDto searchDto) {

        Store store = storeService.findById(id);

        Page<StoreInventory> inventories = storeInventoryRepository.findAll(InventorySpecification.search(store, searchDto), SearchDto.toPageable(searchDto));

        return PageResponse.of(inventories.map(InventoryResDto::from));
    }

    @Transactional
    @CacheEvict(value = "inventory:list", allEntries = true)
    public void adjustInventory(Long storeId, InventoryReqDto reqDto, String uuid) {
        StoreInventory storeInventory = storeInventoryRepository.findByStoreIdAndIngredientId(storeId, reqDto.getIngredientId())
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND));

        BigDecimal currentStock = storeInventory.getCurrentStock();
        BigDecimal quantity = reqDto.getQuantity();

        BigDecimal stockAfter = switch (reqDto.getChangeType()) {
            case IN     -> currentStock.add(quantity);
            case OUT    -> currentStock.subtract(quantity);
            case ADJUST -> quantity;
        };

        if (stockAfter.compareTo(BigDecimal.ZERO) < 0) {
            throw new CustomException(ErrorCode.INSUFFICIENT_STOCK);
        }

        storeInventory.setCurrentStock(stockAfter);

        inventoryLogRepository.save(InventoryLog.builder()
                .store(storeInventory.getStore())
                .ingredient(storeInventory.getIngredient())
                .changeType(reqDto.getChangeType())
                .quantity(reqDto.getQuantity())
                .stockAfter(stockAfter)
                .note(reqDto.getNote())
                .performedBy(uuid)
                .build());
    }

    @Transactional(readOnly = true)
    public PageResponse<InventoryLogResDto> getInventoryLogs(Long storeId, InventoryLogSearchDto searchDto) {

        Page<InventoryLog> inventoryLogs = inventoryLogRepository.findAll(InventoryLogSpecification.search(storeId, searchDto), SearchDto.toPageable(searchDto));

        return PageResponse.of(inventoryLogs.map(InventoryLogResDto::from));
    }
}
