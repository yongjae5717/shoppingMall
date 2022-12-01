package shopping.mall.web.file.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shopping.mall.domain.entity.FileUsage;
import shopping.mall.web.file.dto.*;
import shopping.mall.web.file.service.FileService;

import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "local 파일 API")
public class FileController {

    private final FileService fileService;

    @Operation(summary = "파일 업로드 (POSTMAN) Form-data 등록")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/files")
    public ResponseEntity<UploadReceiptResponse> uploadReceipt(@ModelAttribute @Valid FileRequest fileRequest) {
        return ResponseEntity.ok(fileService.uploadReceipt(fileRequest));
    }

    @Operation(summary = "파일 다운로드")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(value = "/files/download/{name}")
    public ResponseEntity<Resource> loadFile(@PathVariable String name) {
        Resource resource = fileService.loadAsResource(name);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }

    @Operation(summary = "파일 이미지 조회")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/files/images/{name}")
    public ResponseEntity<Resource> showImage(@PathVariable String name) {
        Resource resource = fileService.loadAsResource(name);

        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try {
            filePath = Paths.get(resource.getURI());
            header.add("Content-Type", Files.probeContentType(filePath));
            // 인풋으로 들어온 파일명 .png / .jpg 에 맞게 헤더 타입 설정
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 이미지 리턴 실시 [브라우저에서 get 주소 확인 가능]
        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }

    @Operation(summary = "파일 삭제")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/files/delete/{name}")
    public ResponseEntity<DeleteFileResponse> deleteFile(@PathVariable String name){
        return ResponseEntity.ok(fileService.deleteFiles(name));
    }

    @Operation(summary = "파일 정보 용도별 전체 조회")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/files/{usages}")
    public ResponseEntity<LoadAllByUsageResponse> loadAllByUsage(@PathVariable FileUsage usages) {
        return ResponseEntity.ok(fileService.loadAllByUsage(usages));
    }

}
