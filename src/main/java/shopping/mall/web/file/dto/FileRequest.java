package shopping.mall.web.file.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import shopping.mall.domain.entity.FileUsage;

import javax.validation.constraints.NotNull;

@Data
public class FileRequest {
    @NotNull
    private MultipartFile file;
    @NotNull
    private FileUsage usages;
    @NotNull
    private Long memberId;
}
