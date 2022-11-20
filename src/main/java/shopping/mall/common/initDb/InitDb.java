package shopping.mall.common.initDb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import shopping.mall.domain.entity.Address;
import shopping.mall.domain.entity.Authority;
import shopping.mall.domain.entity.Item.Bottom;
import shopping.mall.domain.entity.Item.Top;
import shopping.mall.domain.entity.User;
import shopping.mall.domain.repository.AuthorityRepository;
import shopping.mall.domain.repository.ItemRepository;
import shopping.mall.domain.repository.UserRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitDb implements CommandLineRunner {
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ItemRepository itemRepository;

    @Override
    public void run(String... args) throws Exception {
        // Default
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
                .build();

        User user2 = User.builder()
                .email("dydwo@naver.com")
                .password(passwordEncoder.encode("123456!@#"))
                .username("김용재")
                .birth("19971020")
                .nickname("hello2")
                .address(new Address("포항", "북구", "22222"))
                .authorities(Collections.singleton(new Authority("ROLE_USER")))
                .activated(true)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        Top top1 = new Top("nike", "90");
        top1.setName("상의 1");
        top1.setPrice(10000);
        top1.setStockQuantity(20);

        Top top2 = new Top("adidas", "90");
        top2.setName("상의 2");
        top2.setPrice(20000);
        top2.setStockQuantity(40);

        Bottom bottom1 = new Bottom("nike", "100");
        bottom1.setName("하의 1");
        bottom1.setPrice(1000);
        bottom1.setStockQuantity(10);

        Bottom bottom2 = new Bottom("fila", "105");
        bottom2.setName("하의 2");
        bottom2.setPrice(2000);
        bottom2.setStockQuantity(20);

        itemRepository.save(top1);
        itemRepository.save(top2);
        itemRepository.save(bottom1);
        itemRepository.save(bottom2);
    }
}