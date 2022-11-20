package shopping.mall.web.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetOrdersResponse {

    private Boolean success;
    private String message;
    private List<OrderQueryDto> findOrders;

    public GetOrdersResponse(Boolean success, String message, List<OrderQueryDto> findOrders) {
        this.success = success;
        this.message = message;
        this.findOrders = findOrders;
    }
}
