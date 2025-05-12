package com.beyond.homs.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
    private final String UPLOAD_DIR = "C:\\be4after\\";

    // 파일 업로드
    public String uploadFile(MultipartFile file, String path) throws IOException {
        if (file == null || file.isEmpty()) {
            System.out.println("업로드할 파일이 없음");
            return null;
        }

        String originalFilename = file.getOriginalFilename();
        int dotIndex = originalFilename.lastIndexOf(".");
        if (dotIndex == -1) {
            throw new IllegalArgumentException("잘못된 파일 형식입니다.");
        }

        // 파일명 중복 방지 (UUID 적용)
        String extension = originalFilename.substring(dotIndex);
        String uuid = UUID.randomUUID().toString();
        String newFileName = uuid + extension;

        // 파일 저장 경로 설정
        Path basePath = Paths.get(UPLOAD_DIR, path);
        Path targetLocation = basePath.resolve(newFileName);

        try {
            // 업로드 디렉토리가 존재하지 않으면 생성
            if (!Files.exists(basePath)) {
                Files.createDirectories(basePath);
                logger.info("파일 업로드 폴더 생성: {}", basePath);
            }

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            logger.info("파일 업로드 성공: {}", targetLocation);

            return targetLocation.toString();

        } catch (IOException e) {
            logger.error("파일 저장 오류: {}, 경로: {}, 에러 메시지: {}", originalFilename, targetLocation, e.getMessage());
            throw e;
        }
    }
    
    // 파일 업데이트
    public String updateFile(MultipartFile file, String path, String originalFilePath) throws IOException {
        // 원본 이미지가 있다면
        if (originalFilePath != null) {
            // 만약에 파일을 업로드 하지 않았다면 그대로 유지
            if(file == null || file.isEmpty()){
                return originalFilePath;
            }
            // 원본 이미지 제거 후 갱신
            else {
                removeFile(originalFilePath);
                return uploadFile(file, path);
            }
        }
        // 원본 이미지가 없다면
        return uploadFile(file, path);
    }

    // 파일 삭제
    public void removeFile(String path) throws IOException {
        Path filePath = Paths.get(path);

        try {
            // 파일이 존재하면 삭제 없으면 무시
            if(Files.exists(filePath)) {
                logger.info("삭제할 파일의 경로: {}", filePath);
                Files.delete(filePath);
            }

        } catch (IOException e) {
            logger.error("파일 삭제 오류 !! 경로: {}, 에러 메시지: {}", filePath, e.getMessage());
            throw e;
        }
    }
}