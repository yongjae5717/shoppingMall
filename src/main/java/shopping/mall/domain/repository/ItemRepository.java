package shopping.mall.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.mall.domain.entity.Item.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}