package shopping.mall.web.order.dto;

import lombok.Data;
import shopping.mall.domain.entity.Item.Item;

import java.util.List;

@Data
public class ItemListResponse {
    private Boolean success;
    private String message;
    private ItemListDto itemList;

    public ItemListResponse(Boolean success, String message, ItemListDto itemList) {
        this.success = success;
        this.message = message;
        this.itemList = itemList;
    }
}
