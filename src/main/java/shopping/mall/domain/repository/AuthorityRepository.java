package shopping.mall.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.mall.domain.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}