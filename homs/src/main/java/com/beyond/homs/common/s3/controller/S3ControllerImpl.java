package com.beyond.homs.common.s3.controller;

import com.beyond.homs.common.exception.exceptions.CustomException;
import com.beyond.homs.common.s3.service.S3Service;
import io.awspring.cloud.s3.S3Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

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
        // 파일 경로 반환
        return ResponseEntity.ok().body(key);
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

    @DeleteMapping("/delete")
    @Override
    public ResponseEntity<String> deleteFile(@RequestParam("key") String key){
        try {
            s3Service.deleteFile(key);
            return ResponseEntity.ok("파일 삭제 성공 "+key);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 삭제 실패 "+e.getMessage());
        }
    }

    // 이미지 받아와서 출력 (스트리밍)
    @GetMapping("/view")
    @Override
    public ResponseEntity<byte[]> streamImage( @RequestParam String key ) throws IOException {

        Resource resource = s3Service.downloadFile(key);

        return s3Service.getImageView(resource);
    }

    @PostMapping("/upload-multiple")
    @Override
    public ResponseEntity<?> uploadMultipleFiles(
            // @RequestParam의 "value"는 프론트엔드의 FormData.append("name", file)의 "name"과 일치해야 합니다.
            @RequestParam(value = "s3Image", required = false) MultipartFile s3Image,
            @RequestParam(value = "s3Msds", required = false) MultipartFile s3Msds,
            @RequestParam(value = "s3Tds1", required = false) MultipartFile s3Tds1,
            @RequestParam(value = "s3Tds2", required = false) MultipartFile s3Tds2,
            @RequestParam(value = "s3Property", required = false) MultipartFile s3Property,
            @RequestParam(value = "s3Guide", required = false) MultipartFile s3Guide) {
        try {
            // S3Service의 uploadProductFiles 메서드를 호출
            Map<String, String> uploadedUrls = s3Service.uploadProductFiles(
                    s3Image, s3Msds, s3Tds1, s3Tds2, s3Property, s3Guide);
            return ResponseEntity.ok(uploadedUrls);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "서버 오류: " + e.getMessage()));
        }
    }
}
