package shopping.mall.web.order.dto;

import lombok.Data;

@Data
public class CancelOrderResponse {
    private Boolean success;
    private String message;

    public CancelOrderResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
