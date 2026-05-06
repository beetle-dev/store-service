package com.cafe.storeservice.service;

import com.cafe.storeservice.common.exception.CustomException;
import com.cafe.storeservice.common.exception.ErrorCode;
import com.cafe.storeservice.common.response.PageResponse;
import com.cafe.storeservice.domain.auth.Role;
import com.cafe.storeservice.domain.store.Store;
import com.cafe.storeservice.dto.*;
import com.cafe.storeservice.dto.store.CreateStoreReqDto;
import com.cafe.storeservice.dto.store.ModifyStoreReqDto;
import com.cafe.storeservice.dto.store.StoreResDto;
import com.cafe.storeservice.dto.store.StoreSearchDto;
import com.cafe.storeservice.repository.*;
import com.cafe.storeservice.specification.StoreSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "store:list", key = "#searchDto.toString()")
    public PageResponse<StoreResDto> getStores(StoreSearchDto searchDto) {

        Page<Store> storeList = storeRepository.findAll(StoreSpecification.search(searchDto), SearchDto.toPageable(searchDto));

        return PageResponse.of(storeList.map(StoreResDto::from));
    }

    @Transactional
    @CacheEvict(value = "store:list", allEntries = true)
    public void register(CreateStoreReqDto reqDto,
                         Role role) {

        if (role != Role.ADMIN) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        storeRepository.save(Store.builder()
                        .name(reqDto.getName())
                        .address(reqDto.getAddress())
                        .phone(reqDto.getPhone())
                        .email(reqDto.getEmail())
                        .openTime(reqDto.getOpenTime())
                        .closeTime(reqDto.getCloseTime())
                        .active(reqDto.isActive())
                .build());
    }

    @Transactional
    @CacheEvict(value = "store:list", allEntries = true)
    public void modify(Long id, ModifyStoreReqDto reqDto) {
         Store store = storeRepository.findById(id)
                 .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

         store.modified(reqDto);
    }

    @Transactional(readOnly = true)
    public Store findById(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND));
    }

    @Transactional
    @CacheEvict(value = "store:list", allEntries = true)
    public void delete(Long id, Role role) {
        if (role != Role.ADMIN) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        storeRepository.delete(store);
    }
}
