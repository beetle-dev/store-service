package com.cafe.storeservice.service;

import com.cafe.storeservice.common.exception.CustomException;
import com.cafe.storeservice.common.exception.ErrorCode;
import com.cafe.storeservice.common.response.PageResponse;
import com.cafe.storeservice.domain.inventory.InventoryLog;
import com.cafe.storeservice.domain.inventory.StoreInventory;
import com.cafe.storeservice.dto.*;
import com.cafe.storeservice.dto.alarm.AlarmEvent;
import com.cafe.storeservice.dto.alarm.AlarmType;
import com.cafe.storeservice.dto.inventory.*;
import com.cafe.storeservice.repository.InventoryLogRepository;
import com.cafe.storeservice.repository.StoreInventoryRepository;
import com.cafe.storeservice.specification.InventoryLogSpecification;
import com.cafe.storeservice.specification.InventorySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private static final BigDecimal NEAR_DEPLETION_RATIO = BigDecimal.valueOf(1.2);

    private final StoreInventoryRepository storeInventoryRepository;
    private final InventoryLogRepository inventoryLogRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Value("${alarm.notification-email}")
    private String notificationEmail;

    @Transactional(readOnly = true)
    @Cacheable(value = "inventory:list", key = "#searchDto.toString()")
    public PageResponse<InventoryResDto> getInventory(InventorySearchDto searchDto) {

        Page<StoreInventory> inventories = storeInventoryRepository.findAll(InventorySpecification.search(searchDto), SearchDto.toPageable(searchDto));

        return PageResponse.of(inventories.map(InventoryResDto::from));
    }

    @Transactional
    @CacheEvict(value = "inventory:list", allEntries = true)
    public void adjustInventory(InventoryReqDto reqDto, String uuid) {
        StoreInventory storeInventory = storeInventoryRepository.findByIngredientId(reqDto.getIngredientId())
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
                .ingredient(storeInventory.getIngredient())
                .changeType(reqDto.getChangeType())
                .quantity(reqDto.getQuantity())
                .stockAfter(stockAfter)
                .note(reqDto.getNote())
                .performedBy(uuid)
                .build());

        publishStockAlarmIfNeeded(storeInventory);
    }

    private void publishStockAlarmIfNeeded(StoreInventory storeInventory) {
        BigDecimal currentStock = storeInventory.getCurrentStock();
        BigDecimal minStock = storeInventory.getMinStock();

        AlarmType type;
        if (currentStock.compareTo(minStock) < 0) {
            type = AlarmType.INVENTORY_LOW;
        } else if (currentStock.compareTo(minStock.multiply(NEAR_DEPLETION_RATIO)) < 0) {
            type = AlarmType.INVENTORY_NEAR_DEPLETION;
        } else {
            return;
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put("ingredientId", storeInventory.getIngredient().getId());
        payload.put("ingredientName", storeInventory.getIngredient().getName());
        payload.put("currentStock", currentStock);
        payload.put("minStock", minStock);
        payload.put("storeEmail", notificationEmail);

        applicationEventPublisher.publishEvent(new AlarmEvent(
                type,
                LocalDateTime.now(),
                payload
        ));
    }

    @Transactional(readOnly = true)
    public PageResponse<InventoryLogResDto> getInventoryLogs(InventoryLogSearchDto searchDto) {

        Page<InventoryLog> inventoryLogs = inventoryLogRepository.findAll(InventoryLogSpecification.search(searchDto), SearchDto.toPageable(searchDto));

        return PageResponse.of(inventoryLogs.map(InventoryLogResDto::from));
    }
}
