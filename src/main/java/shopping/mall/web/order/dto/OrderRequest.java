package shopping.mall.web.order.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private Long itemId;
    private int count;
}
