package com.cafe.storeservice.service;

import com.cafe.storeservice.common.exception.CustomException;
import com.cafe.storeservice.common.exception.ErrorCode;
import com.cafe.storeservice.common.response.PageResponse;
import com.cafe.storeservice.domain.Store;
import com.cafe.storeservice.dto.*;
import com.cafe.storeservice.repository.StoreInventoryRepository;
import com.cafe.storeservice.repository.StoreRepository;
import com.cafe.storeservice.specification.StoreSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreInventoryRepository storeInventoryRepository;

    public PageResponse<StoreResDto> getStores(StoreSearchDto searchDto) {

        Page<Store> storeList = storeRepository.findAll(StoreSpecification.search(searchDto), SearchDto.toPageable(searchDto));

        return PageResponse.of(storeList.map(StoreResDto::from));
    }

    @Transactional
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
    public void modify(Long id, StoreReqDto reqDto) {
         Store store = storeRepository.findById(id)
                 .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

         store.modified(reqDto);
    }

    @Transactional(readOnly = true)
    public List<StoreInventoryResDto> getInventory(Long id) {
        return storeInventoryRepository.findAllByStoreId(id);
    }
}
