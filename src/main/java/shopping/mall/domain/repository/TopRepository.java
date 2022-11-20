package shopping.mall.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.mall.domain.entity.Item.Top;

public interface TopRepository extends JpaRepository<Top, Long> {
}