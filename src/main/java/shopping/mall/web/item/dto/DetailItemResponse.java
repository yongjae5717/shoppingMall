package shopping.mall.web.item.dto;

import lombok.Data;
import shopping.mall.domain.entity.Item.Item;

@Data
public class DetailItemResponse {
    private Boolean success;
    private String message;
    private Item item;

    public DetailItemResponse(Boolean success, String message, Item item) {
        this.success = success;
        this.message = message;
        this.item = item;
    }
}
