package com.cafe.storeservice.service;

import com.cafe.storeservice.common.exception.CustomException;
import com.cafe.storeservice.common.exception.ErrorCode;
import com.cafe.storeservice.common.response.PageResponse;
import com.cafe.storeservice.domain.menu.Menu;
import com.cafe.storeservice.domain.order.Status;
import com.cafe.storeservice.domain.order.Order;
import com.cafe.storeservice.domain.order.OrderItem;
import com.cafe.storeservice.dto.*;
import com.cafe.storeservice.dto.order.*;
import com.cafe.storeservice.repository.OrderItemRepository;
import com.cafe.storeservice.repository.OrderRepository;
import com.cafe.storeservice.specification.OrderSpecification;
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

import static com.cafe.storeservice.common.exception.ErrorCode.VALIDATION_FAILED;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MenuService menuService;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void registerOrder(OrderCreateReqDto orderCreateReqDto) {

        Order newOrder = orderRepository.save(Order.builder()
                .orderNumber(generateOrderNumber())
                .totalAmount(orderCreateReqDto.getTotalAmount())
                .paymentMethod(orderCreateReqDto.getPaymentMethod())
                .build());

        List<OrderItemReqDto> orderItemReqDtoList = orderCreateReqDto.getOrderItemReqDtos();

        List<Long> menuIdList = orderItemReqDtoList.stream().map(OrderItemReqDto::getMenuId).toList();

        Map<Long, Menu> menuMap = menuService.findAllBy(menuIdList)
                .stream()
                .collect(Collectors.toMap(Menu::getId, m -> m));

        List<OrderItem> items = orderItemReqDtoList.stream().map(dto -> {
            Menu menu = menuMap.get(dto.getMenuId());
            if (menu == null) throw new CustomException(ErrorCode.NOT_FOUND);

            return OrderItem.builder()
                    .order(newOrder)
                    .menu(menu)
                    .menuName(menu.getName())
                    .unitPrice(menu.getPrice())
                    .quantity(dto.getQuantity())
                    .subtotal(menu.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())))
                    .build();
        }).toList();

        orderItemRepository.saveAll(items);
    }

    private String generateOrderNumber() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        long sequence = orderRepository.getNextOrderSequence();

        return String.format("ORD-%s-%06d", date, sequence);
    }

    @Transactional(readOnly = true)
    public PageResponse<OrderResDto> getOrders(OrderSearchDto searchDto) {

        Page<Order> orders = orderRepository.findAll(OrderSpecification.search(searchDto), SearchDto.toPageable(searchDto));

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
    public void updateOrder(Long orderId, OrderUpdateReqDto reqDto) {

        Order order = orderRepository.findById(orderId).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND));

        if (reqDto.getStatus() == Status.COMPLETED)
            throw new CustomException(VALIDATION_FAILED);

        order.setStatus(reqDto.getStatus());
    }
}
