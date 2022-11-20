package shopping.mall.web.item.dto;

import lombok.Data;
import shopping.mall.domain.entity.Item.Bottom;
import shopping.mall.domain.entity.Item.Top;

import java.util.List;

@Data
public class ItemListDto {
    private List<Top> topList;
    private List<Bottom> bottomList;

    public ItemListDto(List<Top> topList, List<Bottom> bottomList) {
        this.topList = topList;
        this.bottomList = bottomList;
    }
}
