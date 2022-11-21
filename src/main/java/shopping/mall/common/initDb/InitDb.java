package shopping.mall.common.initDb;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shopping.mall.domain.entity.*;
import shopping.mall.domain.entity.Item.Bottom;
import shopping.mall.domain.entity.Item.Top;
import shopping.mall.domain.repository.AuthorityRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;
        private final AuthorityRepository authorityRepository;
        public void dbInit1() {
            List<Authority> authorities = Arrays.asList(
                    new Authority("ROLE_USER"),
                    new Authority("ROLE_ADMIN")
            );
            authorityRepository.saveAll(authorities);
            User user1 = User.builder()
                    .email("abc@abc.com")
                    .password(passwordEncoder.encode("123456!@#"))
                    .username("관리자")
                    .birth("19971020")
                    .nickname("admin")
                    .address(new Address("서울", "가로수길", "11111"))
                    .authorities(Collections.singleton(new Authority("ROLE_ADMIN")))
                    .activated(true)
                    .orders(new ArrayList<>())
                    .build();
            em.persist(user1);

            Top top1 = new Top("nike", "90");
            top1.setName("상의 1");
            top1.setPrice(10000);
            top1.setStockQuantity(20);

            Top top2 = new Top("adidas", "90");
            top2.setName("상의 2");
            top2.setPrice(20000);
            top2.setStockQuantity(40);

            em.persist(top1);
            em.persist(top2);

            OrderItem orderItem1 = OrderItem.createOrderItem(top1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(top2, 20000, 2);

            Delivery delivery = createDelivery(user1);
            Order order = Order.createOrder(user1, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            User user2 = User.builder()
                    .email("dydwo@naver.com")
                    .password(passwordEncoder.encode("123456!@#"))
                    .username("김용재")
                    .birth("19971020")
                    .nickname("hello2")
                    .address(new Address("포항", "북구", "22222"))
                    .authorities(Collections.singleton(new Authority("ROLE_USER")))
                    .activated(true)
                    .orders(new ArrayList<>())
                    .build();
            em.persist(user2);

            Bottom bottom1 = new Bottom("nike", "100");
            bottom1.setName("하의 1");
            bottom1.setPrice(1000);
            bottom1.setStockQuantity(10);

            Bottom bottom2 = new Bottom("fila", "105");
            bottom2.setName("하의 2");
            bottom2.setPrice(2000);
            bottom2.setStockQuantity(20);

            em.persist(bottom1);
            em.persist(bottom2);

            OrderItem orderItem1 = OrderItem.createOrderItem(bottom1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(bottom2, 40000, 4);

            Delivery delivery = createDelivery(user2);
            Order order = Order.createOrder(user2, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Delivery createDelivery(User user) {
            return new Delivery(user.getAddress(), DeliveryStatus.READY);
        }
    }
}

