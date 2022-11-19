package shopping.mall.web.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shopping.mall.web.order.dto.ItemRequest;
import shopping.mall.web.order.dto.ItemResponse;
import shopping.mall.web.order.service.ItemService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {
    private final ItemService itemService;

    @Operation(description = "상의 등록")
    @PostMapping("/items/new/top")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ItemResponse> createTop(ItemRequest itemRequest) {
        return ResponseEntity.ok(itemService.createTop(itemRequest));
    }

    @Operation(description = "하의 등록")
    @PostMapping("/items/new/bottom")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ItemResponse> createBottom(ItemRequest itemRequest) {
        return ResponseEntity.ok(itemService.createBottom(itemRequest));
    }
}
