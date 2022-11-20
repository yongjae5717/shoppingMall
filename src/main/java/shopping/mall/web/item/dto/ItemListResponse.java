package shopping.mall.web.item.dto;

import lombok.Data;

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
