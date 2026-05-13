package com.cafe.storeservice.dto.history;

import com.cafe.storeservice.domain.SalesStatsHourly;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class SalesStatsHourlyResDto {
    private Long id;
    private Long storeId;
    private LocalDateTime statHour;
    private Integer orderCount;
    private BigDecimal totalSales;
    private BigDecimal cardSales;
    private BigDecimal cashSales;
    private LocalDateTime createdAt;

    public static SalesStatsHourlyResDto from(SalesStatsHourly hourly) {
        return SalesStatsHourlyResDto.builder()
                .id(hourly.getId())
                .storeId(hourly.getStore().getId())
                .statHour(hourly.getStatHour())
                .orderCount(hourly.getOrderCount())
                .totalSales(hourly.getTotalSales())
                .cardSales(hourly.getCardSales())
                .cashSales(hourly.getCashSales())
                .createdAt(hourly.getCreatedAt())
                .build();
    }
}
