package sfa.order_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderResponse {
    private Long orderId;
    private String status;
    private Double totalPrice;
    private Double gstAmount;
    private Double totalPriceWithGst;
    private Date estimatedDeliveryDate;
}

