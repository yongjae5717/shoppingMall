package shopping.mall.web.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import shopping.mall.common.exception.ApiException;
import shopping.mall.common.exception.ExceptionEnum;
import shopping.mall.domain.entity.Item.Bottom;
import shopping.mall.domain.entity.Item.Item;
import shopping.mall.domain.entity.Item.Top;
import shopping.mall.domain.repository.ItemRepository;
import shopping.mall.web.order.dto.ItemRequest;
import shopping.mall.web.order.dto.ItemResponse;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public ItemResponse createTop(ItemRequest itemRequest){
        Top top = new Top(itemRequest.getBrand(), itemRequest.getSize());
        top.setName(itemRequest.getName());
        top.setPrice(itemRequest.getPrice());
        top.setStockQuantity(itemRequest.getStockQuantity());

        saveItem(top);
        return new ItemResponse(true, "상의 등록 성공");
    }

    @Transactional
    public ItemResponse createBottom(ItemRequest itemRequest){
        Bottom bottom = new Bottom(itemRequest.getBrand(), itemRequest.getSize());
        bottom.setName(itemRequest.getName());
        bottom.setPrice(itemRequest.getPrice());
        bottom.setStockQuantity(itemRequest.getStockQuantity());

        saveItem(bottom);
        return new ItemResponse(true, "하의 등록 성공");
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_ITEM));
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_ITEM));
    }

}
