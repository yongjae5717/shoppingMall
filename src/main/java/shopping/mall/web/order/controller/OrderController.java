package shopping.mall.web.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shopping.mall.web.item.service.ItemService;
import shopping.mall.web.order.dto.*;
import shopping.mall.web.order.service.OrderService;
import shopping.mall.web.user.service.UserService;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "주문 API")
public class OrderController {

    private final OrderService orderService;


    @Operation(description = "주문 생성")
    @PostMapping("/order")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<OrderResponse> order(@Valid @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.order(orderRequest));
    }

    @Operation(description = "전체 주문 조회")
    @GetMapping("/orders")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<GetOrdersResponse> orderList() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @Operation(description = "주문 취소")
    @PostMapping("/order/cancel")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<CancelOrderResponse> cancelOrder(@Valid @RequestBody CancelOrderRequest cancelOrderRequest) {
        return ResponseEntity.ok(orderService.cancelOrder(cancelOrderRequest));
    }
}

