package shopping.mall.web.order.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shopping.mall.web.order.dto.OrderItemQueryDto;
import shopping.mall.web.order.dto.OrderQueryDto;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager em;

    public List<OrderQueryDto> findOrders() {
        return em.createQuery(
                        "select new shopping.mall.web.order.dto.OrderQueryDto(o.id, u.username, o.orderDate, o.status, d.address)" +
                                " from Order o" +
                                " join o.user u" +
                                " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }


    /**
     *
     * 컬렉션 조회 최적화
     * 쿼리: 루트 1번, 컬렉션 1번
     * xToOne관계들을 먼저 조회하고, 여기서 얻는 식별자 orderId로 xToMany관계인 OrderItem을 한꺼번에 조회
     * Map을 사용해서 매칭 성능 향상(O(1))
     */
    public List<OrderQueryDto> findAllByDtoOptimization() {
        List<OrderQueryDto> result = findOrders();

        List<Long> orderIds = toOrderIds(result);

        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(orderIds);

        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItems = em.createQuery(
                        "select new shopping.mall.web.order.dto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        // map이용해서 key, value 설정
        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(groupingBy(OrderItemQueryDto::getOrderId));
        return orderItemMap;
    }

    private static List<Long> toOrderIds(List<OrderQueryDto> result) {
        List<Long> orderIds = result.stream()
                .map(o -> o.getOrderId())
                .collect(toList());
        return orderIds;
    }
}
