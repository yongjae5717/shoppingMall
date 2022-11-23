package shopping.mall.web.file.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import shopping.mall.common.exception.ApiException;
import shopping.mall.common.exception.ExceptionEnum;
import shopping.mall.domain.entity.FileInfoEntity;
import shopping.mall.domain.entity.FileUsage;
import shopping.mall.domain.entity.Item.Item;
import shopping.mall.domain.repository.FileRepository;
import shopping.mall.web.file.dto.*;
import shopping.mall.web.item.dto.DetailItemResponse;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final FilePathConfig pathConfig;


    /**
     * 파일 업로드
     */
    @Transactional
    public String save(MultipartFile multipartFile, FileUsage usages, Long memberId) {
        String uploadPath = pathConfig.determinePath(usages); //파일저장경로지정
        FileInfoEntity file = FileInfoEntity.createFileInfo(multipartFile, usages, uploadPath, memberId);
        fileRepository.save(file);
        return file.getName();
    }

    /**
     * 파일 다운로드
     */
    public Resource loadAsResource(String name) {
        FileInfoEntity fileInfo = fileRepository.findOne(name);
        Path filePath = fileInfo.getPullPath(); //파일이 저장되어있는 전체 경로
        try {
            Resource resource = new UrlResource(filePath.toUri()); //URI 생성
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + name);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + name, e);
        }
    }

    /**
     * 파일 삭제
     */
    @Transactional
    public void deleteFile(String name) {
        FileInfoEntity file = fileRepository.findOne(name);
        fileRepository.delete(file);
        file.delete(); // pc에 저장된 파일삭제
    }

    @Transactional
    public UploadReceiptResponse uploadReceipt(FileRequest fileRequest){
        String fileName = save(fileRequest.getFile(), fileRequest.getUsages(), fileRequest.getMemberId());
        return new UploadReceiptResponse(true, "파일 업로드 성공", fileName);
    }


    public LoadAllByUsageResponse loadAllByUsage(FileUsage usages) {
        List<FileDto> fileDtos = fileRepository.findByUsage(usages)
                .stream().map(FileDto::new)
                .collect(Collectors.toList());
        return new LoadAllByUsageResponse(true, "조회 성공", fileDtos);
    }

    @Transactional
    public DeleteFileResponse deleteFiles(String name) {
        deleteFile(name);
        return new DeleteFileResponse(true, "파일 삭제 성공");
    }
}