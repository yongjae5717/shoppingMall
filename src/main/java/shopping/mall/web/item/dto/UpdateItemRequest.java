package shopping.mall.web.item.dto;

import lombok.Data;

@Data
public class UpdateItemRequest {
    private String name;
    private int price;
    private int stockQuantity;
}
