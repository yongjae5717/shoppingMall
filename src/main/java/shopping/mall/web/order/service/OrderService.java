package shopping.mall.web.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.mall.common.exception.ApiException;
import shopping.mall.common.exception.ExceptionEnum;
import shopping.mall.common.security.jwt.SecurityUtil;
import shopping.mall.domain.entity.*;
import shopping.mall.domain.entity.Item.Item;
import shopping.mall.domain.repository.ItemRepository;
import shopping.mall.domain.repository.OrderRepository;
import shopping.mall.domain.repository.OrderSearch;
import shopping.mall.domain.repository.UserRepository;
import shopping.mall.web.order.dto.*;
import shopping.mall.web.order.repository.OrderQueryRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final OrderQueryRepository orderQueryRepository;

    /**
     * 주문
     */
    @Transactional
    public OrderResponse order(OrderRequest orderRequest) {


        User currentUser = SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                .orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_MEMBER));

        Long memberId = currentUser.getUserId();
        Long itemId = orderRequest.getItemId();

        int count = orderRequest.getCount();

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

        return new OrderResponse(true, "주문 완료");
    }


    /**
     * 주문 취소
     */
    @Transactional
    public CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest) {
        Long orderId = cancelOrderRequest.getOrderId();
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
        return new CancelOrderResponse(true, "주문 취소 성공");
    }

    /**
     * 주문 조회
     */
    public GetOrdersResponse getOrders(){
        List<OrderQueryDto> allByDtoOptimization = orderQueryRepository.findAllByDtoOptimization();
        return new GetOrdersResponse(true, "주문 조회 성공", allByDtoOptimization);
    }
}
