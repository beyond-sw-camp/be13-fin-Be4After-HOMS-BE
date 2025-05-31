package com.beyond.homs.common.s3.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface S3Service {
    void uploadFile(MultipartFile file, String key) throws IOException;

    Resource downloadFile(String key);

    // 삭제 메서드
    void deleteFile(String key);

    // 이미지 출력 메서드
    ResponseEntity<byte[]> getImageView(Resource resource) throws IOException;

    // 상품관리 다중 업로드
    Map<String, String> uploadProductFiles(
            Long productId,
            MultipartFile s3Image,
            MultipartFile s3Msds,
            MultipartFile s3Tds1);

    String generateS3Key(String folderPath, String originalFilename);
}
