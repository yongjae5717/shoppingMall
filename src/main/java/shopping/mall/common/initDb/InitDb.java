package shopping.mall.common.initDb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import shopping.mall.domain.entity.Authority;
import shopping.mall.domain.entity.User;
import shopping.mall.domain.repository.AuthorityRepository;
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
                .authorities(Collections.singleton(new Authority("ROLE_ADMIN")))
                .activated(true)
                .build();

        User user2 = User.builder()
                .email("dydwo@naver.com")
                .password(passwordEncoder.encode("123456!@#"))
                .username("김용재")
                .birth("19971020")
                .nickname("hello2")
                .authorities(Collections.singleton(new Authority("ROLE_USER")))
                .activated(true)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
    }
}