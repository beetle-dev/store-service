package com.cafe.storeservice.scheduler;

import com.cafe.storeservice.domain.order.Order;
import com.cafe.storeservice.domain.order.PaymentMethod;
import com.cafe.storeservice.domain.salesQuery.SalesStatsDaily;
import com.cafe.storeservice.domain.salesQuery.SalesStatsHourly;
import com.cafe.storeservice.domain.store.Store;
import com.cafe.storeservice.repository.OrderRepository;
import com.cafe.storeservice.repository.SalesStatsDailyRepository;
import com.cafe.storeservice.repository.SalesStatsHourlyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SalesAggregationScheduler {

    private final OrderRepository orderRepository;
    private final SalesStatsHourlyRepository hourlyRepository;
    private final SalesStatsDailyRepository dailyRepository;

    @Transactional
    @Scheduled(cron = "0 */5 * * * *")
    public void aggregateHourly() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneHourAgo = now.minusHours(1);

        List<Order> orderList = orderRepository.findAllByCreatedAtBetween(oneHourAgo, now);

        Map<Store, List<Order>> groupedByStore = orderList.stream().collect(Collectors.groupingBy(Order::getStore));

        groupedByStore.forEach((store, orders) -> {

            SalesCalcResult result = calcSales(orders);

            hourlyRepository.save(SalesStatsHourly.builder()
                    .store(store)
                    .statHour(oneHourAgo)
                    .orderCount(result.count)
                    .totalSales(result.total)
                    .cardSales(result.card)
                    .cashSales(result.cash)
                    .build());
        });
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void aggregateDaily() {

        LocalDate yesterday = LocalDate.now().minusDays(1);

        List<Order> orders = orderRepository.findAllByCreatedAtBetween(yesterday.atStartOfDay(), yesterday.atTime(23, 59, 59));

        Map<Store, List<Order>> groupByStore = orders.stream().collect(Collectors.groupingBy(Order::getStore));

        groupByStore.forEach((store, orderList) -> {

            SalesCalcResult result = calcSales(orderList);

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
                            .statDate(LocalDate.now().minusDays(1))
                            .orderCount(result.count)
                            .totalSales(result.total)
                            .cardSales(result.card)
                            .cashSales(result.cash)
                            .avgOrderPrice(result.avg)
                            .peakHour(peakHour)
                    .build());
        });
    }

    private record SalesCalcResult(BigDecimal total, BigDecimal card, BigDecimal cash,
                                   Integer count, BigDecimal avg) {}

    private SalesCalcResult calcSales(List<Order> orders) {
        BigDecimal total = orders.stream().map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal card = orders.stream()
                .filter(o -> o.getPaymentMethod() == PaymentMethod.CARD)
                .map(Order::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        Integer count = orders.size();
        BigDecimal avg = count == 0 ? BigDecimal.ZERO
                : total.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
        return new SalesCalcResult(total, card, total.subtract(card), count, avg);
    }
}
