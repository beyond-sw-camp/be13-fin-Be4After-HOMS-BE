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
}