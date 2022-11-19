package shopping.mall.web.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shopping.mall.web.order.service.ItemService;
import shopping.mall.web.order.service.OrderService;
import shopping.mall.web.user.service.UserService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ItemService itemService;

//
//    @GetMapping("/order")
//    public String createForm() {
//
//    }
//
//    @PostMapping("/order")
//    public String order() {
//    }
//
//    @GetMapping("/orders")
//    public String orderList() {
//    }
//
//    @PostMapping("/orders/{orderId}/cancel")
//    public String cancelOrder(@PathVariable("orderId") Long orderId) {
//    }
}

