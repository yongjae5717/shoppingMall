package shopping.mall.web.file.dto;

import lombok.Data;

@Data
public class DeleteFileResponse {
    private Boolean success;
    private String message;

    public DeleteFileResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
