package shopping.mall.web.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.mall.common.exception.ApiException;
import shopping.mall.common.exception.ExceptionEnum;
import shopping.mall.domain.entity.Item.Bottom;
import shopping.mall.domain.entity.Item.Item;
import shopping.mall.domain.entity.Item.Top;
import shopping.mall.domain.repository.BottomRepository;
import shopping.mall.domain.repository.ItemRepository;
import shopping.mall.domain.repository.TopRepository;
import shopping.mall.web.item.dto.*;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final TopRepository topRepository;
    private final BottomRepository bottomRepository;

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
    public List<Top> findTops() {
        return topRepository.findAll();
    }

    public List<Bottom> findBottoms() {
        return bottomRepository.findAll();
    }

    public ItemListResponse getItemList(){
        ItemListDto itemList = new ItemListDto(findTops(), findBottoms());

        return new ItemListResponse(true, "상품 조회 성공", itemList);
    }


    @Transactional
    public UpdateItemResponse updateItem(Long id, UpdateItemRequest updateItemRequest) {
        Item findItem = itemRepository.findById(id).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_ITEM));
        findItem.setName(updateItemRequest.getName());
        findItem.setPrice(updateItemRequest.getPrice());
        findItem.setStockQuantity(updateItemRequest.getStockQuantity());

        return new UpdateItemResponse(true, "상품 수정 성공");
    }

    @Transactional
    public DeleteItemResponse deleteItem(Long id) {
        Item findItem = itemRepository.findById(id).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_ITEM));
        itemRepository.delete(findItem);

        return new DeleteItemResponse(true, "상품 삭제 성공");
    }

    public DetailItemResponse getItem(Long id){
        Item findItem = itemRepository.findById(id).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_ITEM));
        return new DetailItemResponse(true, "상세 상품 조회 성공", findItem);
    }
}
