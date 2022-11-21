package shopping.mall.web.file.dto;

import lombok.Data;
import shopping.mall.domain.entity.FileInfoEntity;

@Data
public class FileDto {
    private String filename;
    private String originalName;

    public FileDto(FileInfoEntity fileInfo){
        filename = fileInfo.getName();
        originalName = fileInfo.getOriginalName();
    }
}