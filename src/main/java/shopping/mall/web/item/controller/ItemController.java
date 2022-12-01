package shopping.mall.web.item.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shopping.mall.web.item.dto.*;
import shopping.mall.web.item.service.ItemService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "상품 API")
public class ItemController {
    private final ItemService itemService;

    @Operation(summary = "상의 등록")
    @PostMapping("/items/new/top")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ItemResponse> createTop(@Valid @RequestBody ItemRequest itemRequest) {
        return ResponseEntity.ok(itemService.createTop(itemRequest));
    }

    @Operation(summary = "하의 등록")
    @PostMapping("/items/new/bottom")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ItemResponse> createBottom(@Valid @RequestBody ItemRequest itemRequest) {
        return ResponseEntity.ok(itemService.createBottom(itemRequest));
    }

    @Operation(summary = "상품 조회")
    @GetMapping("/items")
    public ResponseEntity<ItemListResponse> getItemList(){
        return ResponseEntity.ok(itemService.getItemList());
    }

    @Operation(summary = "상품 수정")
    @PutMapping("/item/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UpdateItemResponse> updateItem(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateItemRequest updateItemRequest){
        return ResponseEntity.ok(itemService.updateItem(id, updateItemRequest));
    }

    @Operation(summary = "상품 삭제")
    @DeleteMapping("item/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<DeleteItemResponse> deleteItem(@PathVariable("id") Long id){
        return ResponseEntity.ok(itemService.deleteItem(id));
    }

    @Operation(summary = "상세 상품 조회")
    @GetMapping("/items/{id}")
    public ResponseEntity<DetailItemResponse> getItem(
            @PathVariable("id") Long id
    ){
        return ResponseEntity.ok(itemService.getItem(id));
    }


}
