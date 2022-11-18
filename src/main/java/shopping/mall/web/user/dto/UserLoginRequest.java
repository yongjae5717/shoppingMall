package shopping.mall.web.user.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserLoginRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
