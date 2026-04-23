package com.cafe.storeservice.dto;

import com.cafe.storeservice.domain.SalesStatsDaily;
import com.cafe.storeservice.domain.Store;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class SalesStatsDailyResDto {
    private Long id;
    private Long storeId;
    private LocalDate statDate;
    private Integer orderCount;
    private BigDecimal totalSales;
    private BigDecimal cardSales;
    private BigDecimal cashSales;
    private BigDecimal avgOrderPrice;
    private Integer peakHour;
    private LocalDateTime createdAt;

    public static SalesStatsDailyResDto from(SalesStatsDaily daily) {
        return SalesStatsDailyResDto.builder()
                .id(daily.getId())
                .storeId(daily.getStore().getId())
                .statDate(daily.getStatDate())
                .orderCount(daily.getOrderCount())
                .totalSales(daily.getTotalSales())
                .cardSales(daily.getCardSales())
                .cashSales(daily.getCashSales())
                .avgOrderPrice(daily.getAvgOrderPrice())
                .peakHour(daily.getPeakHour())
                .createdAt(daily.getCreatedAt())
                .build();
    }
}
