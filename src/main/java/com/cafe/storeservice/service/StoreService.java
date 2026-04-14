package com.cafe.storeservice.service;

import com.cafe.storeservice.common.exception.CustomException;
import com.cafe.storeservice.common.response.ErrorCode;
import com.cafe.storeservice.common.response.PageResponse;
import com.cafe.storeservice.domain.Store;
import com.cafe.storeservice.dto.StoreReqDto;
import com.cafe.storeservice.dto.StoreResDto;
import com.cafe.storeservice.dto.StoreSearchDto;
import com.cafe.storeservice.dto.StoreSpecification;
import com.cafe.storeservice.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public PageResponse<StoreResDto> getStores(StoreSearchDto searchDto) {

        Sort sort = Sort.by(
                Sort.Direction.fromString(searchDto.getDirection()),
                searchDto.getSort()
        );

        Pageable pageable = PageRequest.of(searchDto.getPage(), searchDto.getSize(), sort);

        Page<Store> storeList = storeRepository.findAll(StoreSpecification.search(searchDto), pageable);

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
}
