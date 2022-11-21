package shopping.mall.web.file.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoadAllByUsageResponse {

    private Boolean success;
    private String message;
    private List<FileDto> fileDtos;

    public LoadAllByUsageResponse(Boolean success, String message, List<FileDto> fileDtos) {
        this.success = success;
        this.message = message;
        this.fileDtos = fileDtos;
    }
}
