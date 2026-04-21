package com.cafe.storeservice.service;

import com.cafe.storeservice.common.exception.CustomException;
import com.cafe.storeservice.common.exception.ErrorCode;
import com.cafe.storeservice.common.response.PageResponse;
import com.cafe.storeservice.domain.*;
import com.cafe.storeservice.dto.*;
import com.cafe.storeservice.repository.*;
import com.cafe.storeservice.specification.InventoryLogSpecification;
import com.cafe.storeservice.specification.StoreSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final MenuService menuService;

    private final StoreRepository storeRepository;
    private final StoreInventoryRepository storeInventoryRepository;
    private final InventoryLogRepository inventoryLogRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public PageResponse<StoreResDto> getStores(StoreSearchDto searchDto) {

        Page<Store> storeList = storeRepository.findAll(StoreSpecification.search(searchDto), SearchDto.toPageable(searchDto));

        return PageResponse.of(storeList.map(StoreResDto::from));
    }

    @Transactional
    public void register(StoreReqDto reqDto) {
        storeRepository.save(Store.builder()
                        .name(reqDto.getName())
                        .address(reqDto.getAddress())
                        .phone(reqDto.getPhone())
                        .openTime(reqDto.getOpenTime())
                        .closeTime(reqDto.getCloseTime())
                .build());
    }

    @Transactional
    public void modify(Long id, StoreReqDto reqDto) {
         Store store = storeRepository.findById(id)
                 .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

         store.modified(reqDto);
    }

    @Transactional(readOnly = true)
    public PageResponse<StoreInventoryResDto> getInventory(Long id, SearchDto searchDto) {

        Page<StoreInventory> inventories = storeInventoryRepository.findAllByStoreId(id, SearchDto.toPageable(searchDto));

        return PageResponse.of(inventories.map(StoreInventoryResDto::from));
    }

    @Transactional
    public void adjustInventory(Long storeId, InventoryReqDto reqDto) {
        StoreInventory storeInventory = storeInventoryRepository.findByStoreIdAndIngredientId(storeId, reqDto.getIngredientId())
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND));

        BigDecimal currentStock = storeInventory.getCurrentStock();
        BigDecimal quantity = reqDto.getQuantity();

        BigDecimal stockAfter = switch (reqDto.getChangeType()) {
            case IN     -> currentStock.add(quantity);
            case OUT    -> currentStock.subtract(quantity);
            case ADJUST -> quantity;
        };

        if (stockAfter.compareTo(BigDecimal.ZERO) < 0) {
            throw new CustomException(ErrorCode.INSUFFICIENT_STOCK);
        }

        storeInventory.setCurrentStock(stockAfter);

        // todo performedBy
        inventoryLogRepository.save(InventoryLog.builder()
                        .store(storeInventory.getStore())
                        .ingredient(storeInventory.getIngredient())
                        .changeType(reqDto.getChangeType())
                        .quantity(reqDto.getQuantity())
                        .stockAfter(stockAfter)
                        .note(reqDto.getNote())
                .build());
    }

    @Transactional(readOnly = true)
    public PageResponse<InventoryLogResDto> getInventoryLogs(Long storeId, InventoryLogSearchDto searchDto) {

        storeRepository.findById(storeId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND));

        Page<InventoryLog> inventoryLogs = inventoryLogRepository.findAll(InventoryLogSpecification.search(searchDto), SearchDto.toPageable(searchDto));

        return PageResponse.of(inventoryLogs.map(InventoryLogResDto::from));
    }

    public void registerOrder(Long storeId, OrderCreateReqDto orderCreateReqDto) {

        Order newOrder = orderRepository.save(Order.builder()
                        .store(storeRepository.findById(storeId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND)))
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

        long todayCount = orderRepository.countByOrderedAtBetween(
                LocalDate.now().atStartOfDay(),
                LocalDate.now().atTime(23, 59, 59)
        );

        return String.format("ORD-%s-%06d", date, todayCount + 1);
    }

    @Transactional(readOnly = true)
    public PageResponse<OrderResDto> getOrders(Long storeId, SearchDto searchDto) {

        storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        Page<Order> orders = orderRepository.findAllByStoreId(storeId, SearchDto.toPageable(searchDto));

        List<OrderResDto> orderResDtos = orders.getContent().stream().map(order -> {
            List<OrderItem> items = orderItemRepository.findAllByOrder(order);
            List<OrderItemResDto> orderItemResDtos = items.stream().map(OrderItemResDto::from).toList();
            return OrderResDto.from(order, orderItemResDtos);
        }).toList();

        return PageResponse.of(orderResDtos, orders);
    }

    @Transactional
    public void updateOrder(Long storeId, Long orderId, OrderUpdateReqDto reqDto) {

        storeRepository.findById(storeId).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND));

        Order order = orderRepository.findById(orderId).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND));
        order.setStatus(reqDto.getStatus());
    }
}
