package com.beyond.homs.common.S3.controller;

import com.beyond.homs.common.S3.service.S3Service;
import io.awspring.cloud.s3.S3Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/files/")
public class S3ControllerImpl implements S3Controller {
    private final S3Service s3Service;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("key") String key) {
        try {
            s3Service.uploadFile(file,key);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 실패: " + e.getMessage());
        }
        return ResponseEntity.ok().body("업로드 성공");
    }

    @GetMapping("/download")
    @Override
    public ResponseEntity<Resource> downloadFile(
            @RequestParam("key") String key) {
        Resource resource = s3Service.downloadFile(key);

        S3Resource s3Resource = (S3Resource) resource;
        String contentType = s3Resource.contentType();

        if(resource.exists()) {
            // S3 객체가 존재한다면
            return  ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + key + "\"")
                    .contentType(MediaType.parseMediaType(contentType)) // S3Resource의 Content-Type 사용
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
