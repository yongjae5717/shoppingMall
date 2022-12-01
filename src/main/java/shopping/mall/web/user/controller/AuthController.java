package shopping.mall.web.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.mall.web.user.dto.UserLoginRequest;
import shopping.mall.web.user.dto.UserLoginResponse;
import shopping.mall.web.user.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Tag(name = "인증관리 API")
public class AuthController {

    private final UserService userService;


    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> authorize(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(userService.login(userLoginRequest));

    }
}