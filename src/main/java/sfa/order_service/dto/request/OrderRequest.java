package sfa.order_service.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sfa.order_service.constant.SalesLevelConstant;

@Getter
@Setter
public class OrderRequest {
    private Long productId;
    private int quantity;
    private SalesLevelConstant salesLevel;
}
