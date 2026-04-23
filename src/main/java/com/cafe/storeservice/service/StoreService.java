package com.cafe.storeservice.service;

import com.cafe.storeservice.common.exception.CustomException;
import com.cafe.storeservice.common.exception.ErrorCode;
import com.cafe.storeservice.common.response.PageResponse;
import com.cafe.storeservice.domain.*;
import com.cafe.storeservice.dto.*;
import com.cafe.storeservice.repository.*;
import com.cafe.storeservice.specification.InventoryLogSpecification;
import com.cafe.storeservice.specification.StoreSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final MenuService menuService;

    private final StoreRepository storeRepository;
    private final StoreInventoryRepository storeInventoryRepository;
    private final InventoryLogRepository inventoryLogRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final SalesStatsDailyRepository dailyRepository;
    private final SalesStatsHourlyRepository hourlyRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "store:list", key = "#searchDto.toString()")
    public PageResponse<StoreResDto> getStores(StoreSearchDto searchDto) {

        Page<Store> storeList = storeRepository.findAll(StoreSpecification.search(searchDto), SearchDto.toPageable(searchDto));

        return PageResponse.of(storeList.map(StoreResDto::from));
    }

    @Transactional
    @CacheEvict(value = "store:list", allEntries = true)
    public void register(StoreReqDto reqDto) {
        storeRepository.save(Store.builder()
                        .name(reqDto.getName())
                        .address(reqDto.getAddress())
                        .phone(reqDto.getPhone())
                        .openTime(reqDto.getOpenTime())
                        .closeTime(reqDto.getCloseTime())
                .build());
    }

    @Transactional
    @CacheEvict(value = "store:list", allEntries = true)
    public void modify(Long id, StoreReqDto reqDto) {
         Store store = storeRepository.findById(id)
                 .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

         store.modified(reqDto);
    }

    @Transactional(readOnly = true)
    public Store findById(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND));
    }
}
