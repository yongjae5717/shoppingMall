package shopping.mall.web.file.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import shopping.mall.domain.entity.FileUsage;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
@Service
@Slf4j
public class FilePathConfig {
    @Value("${file.upload.top}")
    private String top;
    @Value("${file.upload.bottom}")
    private String bottom;

    /**
     * 파일을 저장할 디렉토리 초기화
     */
    @PostConstruct
    private void init() {
        try {
            Files.createDirectories(Paths.get(top));
            Files.createDirectories(Paths.get(bottom));
            log.info("fileInit");
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }


    /**
     * 파일저장경로지정
     */
    String determinePath(FileUsage usages) {
        String path = "";
        switch (usages) {
            case TOP:
                path = this.top;
                break;
            case BOTTOM:
                path = this.bottom;
                break;
        }
        return path;
    }

}