package shopping.mall.common.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {
    /**
     * 회원가입 로그인 에러
     */
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "E000", "이미 가입되어 있는 이메일입니다."), // 400
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "E001", "멤버를 찾을 수 없습니다."), // 404
    MISSING_REQUIRED_ITEMS(HttpStatus.BAD_REQUEST, "E002", "필수 항목이 누락되었습니다."), //400
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "E003", "닉네임이 중복됩니다."),
    TOKEN_EMPTY(HttpStatus.UNAUTHORIZED, "E004", "토근을 보유하고 있지 않습니다."),
    NOT_FOUND_EMAIL(HttpStatus.BAD_REQUEST, "E005", "이메일을 찾을 수 없습니다."),
    NOT_ENOUGH_STOCK(HttpStatus.BAD_REQUEST, "E006", "재고가 모두 소진되었습니다."),
    NOT_FOUND_ITEM(HttpStatus.BAD_REQUEST, "E007", "아이템을 찾을 수 없습니다."),
    NOT_MATCH_EXT(HttpStatus.BAD_REQUEST, "E008", "확장자가 다릅니다.");


    private final HttpStatus status;
    private final String code;
    private String message;

    ExceptionEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
