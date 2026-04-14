package com.cafe.storeservice.service;

import com.cafe.storeservice.common.response.PageResponse;
import com.cafe.storeservice.domain.Store;
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
}
