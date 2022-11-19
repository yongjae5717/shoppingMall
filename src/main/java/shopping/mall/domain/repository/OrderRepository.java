package shopping.mall.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import shopping.mall.domain.entity.OrderStatus;

import javax.persistence.EntityManager;

import javax.persistence.criteria.*;
import java.util.List;

import static shopping.mall.domain.entity.QOrder.order;
import static shopping.mall.domain.entity.QUser.user;

@Repository
public class OrderRepository {

    private final EntityManager em;

    public OrderRepository(EntityManager em) {
        this.em = em;
    }

    public void save(shopping.mall.domain.entity.Order order) {
        em.persist(order);
    }

    public shopping.mall.domain.entity.Order findOne(Long id) {
        return em.find(shopping.mall.domain.entity.Order.class, id);
    }

    // fetch join 사용해서 N+1 문제 해결
    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class
        ).getResultList();
    }

    // 주문조회 fetch join사용 (페이징 문제 발생)
    public List<shopping.mall.domain.entity.Order> findAllWithItem(){
        return em.createQuery(
                        "select distinct o from Order o" +
                                " join fetch o.member m" +
                                " join fetch o.delivery d" +
                                " join fetch o.orderItems oi" +
                                " join fetch oi.item i", shopping.mall.domain.entity.Order.class)
                .getResultList();
    }

    // 주문조회 페이징문제 해결 (쿼리 호출 수가 1 + N -> 1 + 1로 최적화된다.)
    public List<shopping.mall.domain.entity.Order> findAllWithMemberDeliveryV2(int offset, int limit) {
        return em.createQuery(
                        "select o from Order o" +
                                " join fetch o.member m" +
                                " join fetch o.delivery d", shopping.mall.domain.entity.Order.class
                )
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    // Query Dsl
    public List<shopping.mall.domain.entity.Order> findAll(OrderSearch orderSearch){
        JPAQueryFactory query = new JPAQueryFactory(em);


        return query
                .select(order)
                .from(order)
                .join(order.user, user)
                .where(statusEq(orderSearch.getOrderStatus()), nameLike(orderSearch.getMemberName()))
                .limit(1000)
                .fetch();
    }

    // Query Dsl 조건 추가
    private static BooleanExpression nameLike(String memberName) {
        if (!StringUtils.hasText(memberName)){
            return null;
        }
        return user.username.like(memberName);
    }

    private BooleanExpression statusEq(OrderStatus statusCond){
        if (statusCond == null){
            return null;
        }
        return order.status.eq(statusCond);
    }
}