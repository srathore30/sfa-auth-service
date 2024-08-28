package sfa.order_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sfa.order_service.dto.request.OrderRequest;
import sfa.order_service.dto.request.OrderUpdateRequest;
import sfa.order_service.dto.response.OrderResponse;
import sfa.order_service.dto.response.PaginatedResp;
import sfa.order_service.entity.OrderEntity;
import sfa.order_service.repo.OrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderRequest request) {
        log.info("Creating order: {}", request);
        OrderEntity entity = orderRepository.save(dtoToEntity(request));
        return entityToDto(entity);
    }

    public OrderEntity dtoToEntity(OrderRequest request) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setQuantity(request.getQuantity());
        orderEntity.setSalesLevel(request.getSalesLevel());
        orderEntity.setProductId(request.getProductId());
        return orderEntity;
    }

    public OrderResponse entityToDto(OrderEntity orderEntity) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setStatus(orderEntity.getStatus());
        orderResponse.setOrderId(orderEntity.getId());
        return orderResponse;
    }

    public PaginatedResp<OrderResponse> getOrderById(Long orderId, int page, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<OrderEntity> byId = orderRepository.findById(orderId, pageable);
        List<OrderResponse> collect = byId.getContent().stream().map(this::entityToDto).collect(Collectors.toList());
        return PaginatedResp.<OrderResponse>builder().totalElements(byId.getTotalElements()).totalPages(byId.getTotalPages()).page(page).content(collect).build();
    }

    public OrderResponse updateOrder(Long orderId, OrderUpdateRequest request) {
        Optional<OrderEntity> byId = orderRepository.findById(orderId);
        if (byId.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        OrderEntity orderEntity = byId.get();
        orderEntity.setStatus(request.getStatus());
        return entityToDto(orderRepository.save(orderEntity));
    }

}
