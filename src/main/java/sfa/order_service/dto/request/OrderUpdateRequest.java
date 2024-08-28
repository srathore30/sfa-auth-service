package sfa.order_service.dto.request;

import lombok.Getter;
import lombok.Setter;
import sfa.order_service.constant.OrderStatus;

@Getter
@Setter
public class OrderUpdateRequest {
    private OrderStatus status;
}
