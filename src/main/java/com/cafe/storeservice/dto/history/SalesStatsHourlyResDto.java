package com.cafe.storeservice.dto.history;

import com.cafe.storeservice.domain.salesQuery.SalesStatsHourly;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class SalesStatsHourlyResDto {
    private Long id;
    private LocalDateTime statHour;
    private Integer orderCount;
    private BigDecimal totalSales;
    private BigDecimal cardSales;
    private BigDecimal cashSales;
    private LocalDateTime createdAt;

    public static SalesStatsHourlyResDto from(SalesStatsHourly hourly) {
        return SalesStatsHourlyResDto.builder()
                .id(hourly.getId())
                .statHour(hourly.getStatHour())
                .orderCount(hourly.getOrderCount())
                .totalSales(hourly.getTotalSales())
                .cardSales(hourly.getCardSales())
                .cashSales(hourly.getCashSales())
                .createdAt(hourly.getCreatedAt())
                .build();
    }
}
