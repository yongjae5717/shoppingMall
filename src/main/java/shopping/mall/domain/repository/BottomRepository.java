package shopping.mall.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.mall.domain.entity.Item.Bottom;

public interface BottomRepository extends JpaRepository<Bottom, Long> {
}
