package shopping.mall.web.item.dto;

import lombok.Data;

@Data
public class UpdateItemResponse {
    private Boolean success;
    private String message;

    public UpdateItemResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
