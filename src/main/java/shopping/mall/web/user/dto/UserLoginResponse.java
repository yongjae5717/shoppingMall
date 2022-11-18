package shopping.mall.web.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {
    private Boolean success;

    private String message;

    private TokenDto tokenDto;

    private UserDto userDto;
}