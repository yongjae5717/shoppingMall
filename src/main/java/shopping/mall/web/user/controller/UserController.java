package shopping.mall.web.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shopping.mall.web.user.dto.*;
import shopping.mall.web.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@Tag(name = "회원관리 API")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(description = "redirect 테스트")
    @PostMapping("/test-redirect")
    public void testRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/user");
    }

    @Operation(description = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<UserCreateResponse> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @Operation(description = "회원정보 검색")
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserDto> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }

    @Operation(description = "관리자용 회원정보 검색")
    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
    }

    @Operation(description = "회원정보 수정")
    @PutMapping("/user/update")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @Valid @RequestBody UpdateUserRequest updateUserRequest){
        return ResponseEntity.ok(userService.updateUser(updateUserRequest));
    }

    @Operation(description = "회원 탈퇴")
    @DeleteMapping("/user/delete")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<DeleteUserResponse> deleteUser(){
        return ResponseEntity.ok(userService.deleteUser());
    }

}