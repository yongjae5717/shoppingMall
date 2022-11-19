package shopping.mall.domain.entity.Item;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter
@NoArgsConstructor
public class Bottom extends Item{

    private String brand;
    private String size;

    public Bottom(String brand, String size) {
        this.brand = brand;
        this.size = size;
    }
}
