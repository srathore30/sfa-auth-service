package sfa.order_service.dto.request;

import lombok.Getter;
import lombok.Setter;
import sfa.order_service.constant.SalesLevelConstant;

@Getter
@Setter
public class OrderRequest {
    private int quantity;
    private SalesLevelConstant salesLevel;
}
