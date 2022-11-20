package shopping.mall.web.order.dto;

import lombok.Data;

@Data
public class OrderResponse {

    private Boolean success;
    private String message;

    public OrderResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
