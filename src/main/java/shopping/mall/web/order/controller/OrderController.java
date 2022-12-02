package shopping.mall.web.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.mall.web.order.dto.*;
import shopping.mall.web.order.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "주문 API")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 등록")
    @PostMapping("/order")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<OrderResponse> order(OrderRequest orderRequest){
        return ResponseEntity.ok(orderService.order(orderRequest));
    }

    @Operation(summary = "주문 취소")
    @PostMapping("/order/cancel")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<CancelOrderResponse> cancel(CancelOrderRequest cancelOrderRequest){
        return ResponseEntity.ok(orderService.cancel(cancelOrderRequest));
    }

    @Operation(summary = "주문 조회")
    @GetMapping("/orders")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<GetOrdersResponse> viewOrders(){
        return ResponseEntity.ok(orderService.viewOrders());
    }
}
