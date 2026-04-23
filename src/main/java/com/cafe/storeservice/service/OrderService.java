package com.cafe.storeservice.service;

import com.cafe.storeservice.common.exception.CustomException;
import com.cafe.storeservice.common.exception.ErrorCode;
import com.cafe.storeservice.common.response.PageResponse;
import com.cafe.storeservice.domain.Menu;
import com.cafe.storeservice.domain.Order;
import com.cafe.storeservice.domain.OrderItem;
import com.cafe.storeservice.dto.*;
import com.cafe.storeservice.repository.OrderItemRepository;
import com.cafe.storeservice.repository.OrderRepository;
import com.cafe.storeservice.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final StoreService storeService;
    private final MenuService menuService;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void registerOrder(Long storeId, OrderCreateReqDto orderCreateReqDto) {

        Order newOrder = orderRepository.save(Order.builder()
                .store(storeService.findById(storeId))
                .orderNumber(generateOrderNumber())
                .totalAmount(orderCreateReqDto.getTotalAmount())
                .paymentMethod(orderCreateReqDto.getPaymentMethod())
                .build());

        List<OrderItemReqDto> orderItemReqDtoList = orderCreateReqDto.getOrderItemReqDtos();
        orderItemReqDtoList.forEach(dto -> {
            Menu menu = menuService.findById(dto.getMenuId());
            orderItemRepository.save(OrderItem.builder()
                    .order(newOrder)
                    .menu(menu)
                    .menuName(menu.getName())
                    .unitPrice(menu.getPrice())
                    .quantity(dto.getQuantity())
                    .subtotal(menu.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())))
                    .build());
        });
    }

    private String generateOrderNumber() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        long todayCount = orderRepository.countByCreatedAtBetween(
                LocalDate.now().atStartOfDay(),
                LocalDate.now().atTime(23, 59, 59)
        );

        return String.format("ORD-%s-%06d", date, todayCount + 1);
    }

    @Transactional(readOnly = true)
    public PageResponse<OrderResDto> getOrders(Long storeId, SearchDto searchDto) {

        storeService.findById(storeId);

        Page<Order> orders = orderRepository.findAllByStoreId(storeId, SearchDto.toPageable(searchDto));

        List<Long> orderIds = orders.getContent().stream()
                .map(Order::getId)
                .toList();

        Map<Long, List<OrderItem>> itemMap = orderItemRepository
                .findAllByOrderIdIn(orderIds)
                .stream()
                .collect(Collectors.groupingBy(item -> item.getOrder().getId()));

        List<OrderResDto> orderResDtos = orders.getContent().stream()
                .map(order -> {
                    List<OrderItemResDto> orderItemResDtos = itemMap
                            .getOrDefault(order.getId(), List.of())
                            .stream()
                            .map(OrderItemResDto::from)
                            .toList();
                    return OrderResDto.from(order, orderItemResDtos);
                })
                .toList();

        return PageResponse.of(orderResDtos, orders);
    }

    @Transactional
    public void updateOrder(Long storeId, Long orderId, OrderUpdateReqDto reqDto) {

        storeService.findById(storeId);

        Order order = orderRepository.findById(orderId).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND));
        order.setStatus(reqDto.getStatus());
    }
}
