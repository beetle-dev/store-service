package com.cafe.storeservice.scheduler;

import com.cafe.storeservice.domain.*;
import com.cafe.storeservice.repository.OrderRepository;
import com.cafe.storeservice.repository.SalesStatsDailyRepository;
import com.cafe.storeservice.repository.SalesStatsHourlyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SalesAggregationScheduler {

    private final OrderRepository orderRepository;
    private final SalesStatsHourlyRepository hourlyRepository;
    private final SalesStatsDailyRepository dailyRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void aggregateHourly() {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1)
                .truncatedTo(ChronoUnit.HOURS);
        LocalDateTime now = LocalDateTime.now()
                .truncatedTo(ChronoUnit.HOURS);

        List<Order> orderList = orderRepository.findAllAtBetween(oneHourAgo, now);

        Map<Store, List<Order>> groupedByStore = orderList.stream().collect(Collectors.groupingBy(Order::getStore));

        groupedByStore.forEach((store, orders) -> {
            Integer orderCount = orders.size();

            BigDecimal totalSales = orders.stream()
                    .map(Order::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal cardSales = orders.stream()
                    .filter(o -> o.getPaymentMethod() == PaymentMethod.CARD)
                    .map(Order::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal cashSales = orders.stream()
                    .filter(o -> o.getPaymentMethod() == PaymentMethod.CASH)
                    .map(Order::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            hourlyRepository.save(SalesStatsHourly.builder()
                    .store(store)
                    .statHour(LocalDateTime.now())
                    .orderCount(orderCount)
                    .totalSales(totalSales)
                    .cardSales(cardSales)
                    .cashSales(cashSales)
                    .build());
        });
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void aggregateDaily() {

        LocalDateTime yesterday = LocalDate.now().minusDays(1).atStartOfDay();

        List<Order> orders = orderRepository.findAllAtBetween(yesterday, LocalDateTime.now());
        // → sales_stats_daily에 저장

        Map<Store, List<Order>> groupByStore = orders.stream().collect(Collectors.groupingBy(Order::getStore));

        groupByStore.forEach((store, orderList) -> {
            Integer orderCount = orderList.size();

            BigDecimal totalSales = orderList.stream()
                    .map(Order::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal cardSales = orderList.stream()
                    .filter(o -> o.getPaymentMethod() == PaymentMethod.CARD)
                    .map(Order::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal cashSales = orderList.stream()
                    .filter(o -> o.getPaymentMethod() == PaymentMethod.CASH)
                    .map(Order::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal avgOrderPrice = totalSales.divide(
                    BigDecimal.valueOf(orderCount),
                    2,
                    RoundingMode.HALF_UP);

            Integer peakHour = orderList.stream()
                    .collect(Collectors.groupingBy(
                            o -> o.getCreatedAt().getHour(),
                            Collectors.counting()
                    ))
                    .entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);

            dailyRepository.save(SalesStatsDaily.builder()
                            .store(store)
                            .statDate(LocalDate.now())
                            .orderCount(orderCount)
                            .totalSales(totalSales)
                            .cardSales(cardSales)
                            .cashSales(cashSales)
                            .avgOrderPrice(avgOrderPrice)
                            .peakHour(peakHour)
                    .build());
        });
    }
}
