package shopping.mall.web.order.dto;

import lombok.Data;

@Data
public class ItemRequest {
    private String brand;
    private String size;
    private String name;
    private int price;
    private int stockQuantity;
}
