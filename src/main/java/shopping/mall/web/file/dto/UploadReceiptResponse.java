package shopping.mall.web.file.dto;

import lombok.Data;

@Data
public class UploadReceiptResponse {

    private Boolean success;
    private String message;
    private String fileName;

    public UploadReceiptResponse(Boolean success, String message, String fileName) {
        this.success = success;
        this.message = message;
        this.fileName = fileName;
    }
}
