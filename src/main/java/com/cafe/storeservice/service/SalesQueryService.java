package com.cafe.storeservice.service;

import com.cafe.storeservice.common.response.PageResponse;
import com.cafe.storeservice.domain.salesQuery.SalesStatsDaily;
import com.cafe.storeservice.domain.salesQuery.SalesStatsHourly;
import com.cafe.storeservice.dto.history.SalesHistorySearchDto;
import com.cafe.storeservice.dto.history.SalesStatsDailyResDto;
import com.cafe.storeservice.dto.history.SalesStatsHourlyResDto;
import com.cafe.storeservice.dto.SearchDto;
import com.cafe.storeservice.repository.SalesStatsDailyRepository;
import com.cafe.storeservice.repository.SalesStatsHourlyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "sales:daily", key = "#id + ':' + #searchDto.toString()")
    public PageResponse<SalesStatsDailyResDto> getSalesDailyHistory(Long id, SalesHistorySearchDto searchDto) {

        storeService.findById(id);

        LocalDate from = searchDto.getFrom().toLocalDate();
        LocalDate to = searchDto.getTo().toLocalDate();

        Page<SalesStatsDaily> dailies = dailyRepository.findByStoreIdAndStatDateBetween(id, from, to, SearchDto.toPageable(searchDto));

        return PageResponse.of(dailies.map(SalesStatsDailyResDto::from));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "sales:hourly", key = "#id + ':' + #searchDto.toString()")
    public PageResponse<SalesStatsHourlyResDto> getSalesHourlyHistory(Long id, SalesHistorySearchDto searchDto) {

        storeService.findById(id);

        LocalDateTime from = searchDto.getFrom();
        LocalDateTime to = searchDto.getTo();

        Page<SalesStatsHourly> hourlies = hourlyRepository.findByStoreIdAndStatHourBetween(id, from, to, SearchDto.toPageable(searchDto));

        return PageResponse.of(hourlies.map(SalesStatsHourlyResDto::from
        ));
    }
}
