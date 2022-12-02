package shopping.mall.web.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.mall.common.exception.ApiException;
import shopping.mall.common.exception.ExceptionEnum;
import shopping.mall.common.security.jwt.SecurityUtil;
import shopping.mall.domain.entity.*;
import shopping.mall.domain.entity.Item.Item;
import shopping.mall.domain.repository.ItemRepository;
import shopping.mall.domain.repository.OrderRepository;
import shopping.mall.domain.repository.UserRepository;
import shopping.mall.web.order.dto.*;
import shopping.mall.web.order.repository.OrderQueryRepository;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;


    @Transactional
    public OrderResponse order(OrderRequest orderRequest) {
        User findUser = SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                .orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_MEMBER));
        Item item = itemRepository.findById(orderRequest.getItemId()).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_ITEM));

        Delivery delivery = new Delivery(findUser.getAddress(), DeliveryStatus.READY);

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), orderRequest.getCount());

        Order order = Order.createOrder(findUser, delivery, orderItem);

        orderRepository.save(order);

        return new OrderResponse(true, "주문 요청 성공");
    }

    @Transactional
    public CancelOrderResponse cancel(CancelOrderRequest cancelOrderRequest){

        Order order = orderRepository.findOne(cancelOrderRequest.getOrderId());

        if(order.getStatus() == OrderStatus.CANCEL){
            throw new ApiException(ExceptionEnum.ALREADY_CANCEL);
        }
        //주문 취소
        order.cancel();

        return new CancelOrderResponse(true, "주문 취소 성공");
    }

    public GetOrdersResponse viewOrders() {
        List<OrderQueryDto> allByDtoOptimization = orderQueryRepository.findAllByDtoOptimization();
        return new GetOrdersResponse(true, "전체 주문 조회 성공", allByDtoOptimization);
    }
}
