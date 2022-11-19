package shopping.mall.domain.entity.Item;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("T")
@Getter
@NoArgsConstructor
public class Top extends Item{

    private String brand;
    private String size;
}
