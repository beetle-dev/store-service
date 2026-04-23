package com.cafe.storeservice.service;

import com.cafe.storeservice.common.exception.CustomException;
import com.cafe.storeservice.common.exception.ErrorCode;
import com.cafe.storeservice.common.response.PageResponse;
import com.cafe.storeservice.domain.SalesStatsDaily;
import com.cafe.storeservice.domain.SalesStatsHourly;
import com.cafe.storeservice.dto.SalesHistorySearchDto;
import com.cafe.storeservice.dto.SalesStatsDailyResDto;
import com.cafe.storeservice.dto.SalesStatsHourlyResDto;
import com.cafe.storeservice.dto.SearchDto;
import com.cafe.storeservice.repository.SalesStatsDailyRepository;
import com.cafe.storeservice.repository.SalesStatsHourlyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SalesQueryService {

    private final StoreService storeService;

    private final SalesStatsDailyRepository dailyRepository;
    private final SalesStatsHourlyRepository hourlyRepository;

    @Transactional(readOnly = true)
    public PageResponse<SalesStatsDailyResDto> getSalesDailyHistory(Long id, SalesHistorySearchDto searchDto) {

        storeService.findById(id);

        LocalDate from = searchDto.getFrom().toLocalDate();
        LocalDate to = searchDto.getTo().toLocalDate();

        Page<SalesStatsDaily> dailies = dailyRepository.findByStoreIdAndStatDateBetween(id, from, to, SearchDto.toPageable(searchDto));

        return PageResponse.of(dailies.map(SalesStatsDailyResDto::from));
    }

    @Transactional(readOnly = true)
    public Object getSalesHourlyHistory(Long id, SalesHistorySearchDto searchDto) {

        storeService.findById(id);

        LocalDateTime from = searchDto.getFrom();
        LocalDateTime to = searchDto.getTo();

        Page<SalesStatsHourly> hourlies = hourlyRepository.findByStoreIdAndStatHourBetween(id, from, to, SearchDto.toPageable(searchDto));

        return PageResponse.of(hourlies.map(SalesStatsHourlyResDto::from
        ));
    }
}
