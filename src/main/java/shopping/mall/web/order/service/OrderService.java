package shopping.mall.web.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.mall.common.exception.ApiException;
import shopping.mall.common.exception.ExceptionEnum;
import shopping.mall.domain.entity.*;
import shopping.mall.domain.entity.Item.Item;
import shopping.mall.domain.repository.ItemRepository;
import shopping.mall.domain.repository.OrderRepository;
import shopping.mall.domain.repository.OrderSearch;
import shopping.mall.domain.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        User user = userRepository.findById(memberId).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_MEMBER));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_ITEM));

        //배송정보 생성
        Delivery delivery = new Delivery(user.getAddress(), DeliveryStatus.READY);

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(user, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }


    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }
}
