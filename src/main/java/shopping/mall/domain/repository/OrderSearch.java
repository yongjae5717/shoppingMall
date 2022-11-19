package shopping.mall.domain.repository;

import lombok.Getter;
import lombok.Setter;
import shopping.mall.domain.entity.OrderStatus;

@Getter
@Setter
public class OrderSearch {

    private String memberName; //회원 이름
    private OrderStatus orderStatus; //주문 상태[ORDER, CANCEL]
}