package com.cafe.storeservice.scheduler;

import com.cafe.storeservice.domain.menu.Menu;
import com.cafe.storeservice.domain.order.PaymentMethod;
import com.cafe.storeservice.domain.store.Store;
import com.cafe.storeservice.dto.order.OrderCreateReqDto;
import com.cafe.storeservice.dto.order.OrderItemReqDto;
import com.cafe.storeservice.repository.MenuRepository;
import com.cafe.storeservice.repository.StoreRepository;
import com.cafe.storeservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class OrderSimulationScheduler {
    
    @Value("${simulation.enabled}")
    private Boolean simulationEnabled;
    
    @Value("${simulation.orders-per-store-min}")
    private Integer min;
    
    @Value("${simulation.orders-per-store-max}")
    private Integer max;
    
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final Random random;
    private final OrderService orderService;

    @Scheduled(cron = "0 */2 * * * *")
    public void generateDailyOrders() {
        if (!simulationEnabled) return;

        List<Store> stores = storeRepository.findAll();
        for (Store store : stores) {
            List<Menu> menus = menuRepository.findByIsActiveTrue();
            if (menus.isEmpty()) continue;

            int orderCount = random.nextInt(max - min + 1) + min;
            for (int i = 0; i < orderCount; i++) {
                OrderCreateReqDto dto = buildRandomOrder(menus);
                orderService.registerOrder(store.getId(), dto);
            }
        }
    }

    private OrderCreateReqDto buildRandomOrder(List<Menu> menus) {

        int orderItemCnt = random.nextInt(4) + 1;
        orderItemCnt = Math.min(orderItemCnt, menus.size());
        Collections.shuffle(menus);
        List<Menu> selectedMenus = menus.subList(0, orderItemCnt);

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItemReqDto> orderItemReqDtos = new ArrayList<>();

        for (Menu menu : selectedMenus) {
            int qty = random.nextInt(10) + 1;
            totalAmount = totalAmount.add(menu.getPrice().multiply(BigDecimal.valueOf(qty)));
            orderItemReqDtos.add(OrderItemReqDto.builder()
                    .menuId(menu.getId())
                    .quantity(qty)
                    .build());
        }

        int paymentMethodRand = random.nextInt(100);

        PaymentMethod method;
        if (paymentMethodRand < 60) {
            method = PaymentMethod.CARD;
        } else if (paymentMethodRand < 85) {
            method = PaymentMethod.CASH;
        } else {
            method = PaymentMethod.APP;
        }

        return OrderCreateReqDto.builder()
                .totalAmount(totalAmount)
                .paymentMethod(method)
                .orderItemReqDtos(orderItemReqDtos)
                .build();
    }

}
