package sfa.order_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sfa.order_service.dto.request.OrderRequest;
import sfa.order_service.dto.response.OrderResponse;
import sfa.order_service.entity.OrderEntity;
import sfa.order_service.repo.OrderRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
   public OrderResponse createOrder(OrderRequest request){
       log.info("Creating order: {}", request);
       OrderEntity entity = orderRepository.save(dtoToEntity(request));
       return entityToDto(entity);
   }

   public OrderEntity dtoToEntity(OrderRequest request) {
       OrderEntity orderEntity = new OrderEntity();
       orderEntity.setQuantity(request.getQuantity());
       orderEntity.setPrice(request.getQuantity());
       orderEntity.setSalesLevel(request.getSalesLevel());
       return orderEntity;
   }
   public OrderResponse entityToDto(OrderEntity orderEntity) {
       OrderResponse orderResponse = new OrderResponse();
       orderResponse.setStatus(orderEntity.getStatus().toString());
       orderResponse.setOrderId(orderEntity.getId());
       return orderResponse;
   }
}
