package shopping.mall.domain.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.mall.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 쿼리 수행시 Lazy조회가 아닌 Eager조회를 통한 authorities를 같이 가지고 온다.
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);

    Optional<User> findOneByEmail(String email);
    Optional<User> findByNickname(String nickname);
}