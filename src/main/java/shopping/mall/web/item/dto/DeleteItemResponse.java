package shopping.mall.web.item.dto;

import lombok.Data;

@Data
public class DeleteItemResponse {
    private Boolean success;
    private String message;

    public DeleteItemResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
