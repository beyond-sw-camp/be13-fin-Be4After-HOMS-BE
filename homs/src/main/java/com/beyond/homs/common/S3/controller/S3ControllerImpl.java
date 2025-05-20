package com.beyond.homs.common.S3.controller;

import com.beyond.homs.common.S3.service.S3Service;
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
    public ResponseEntity<byte[]> streamImage( @RequestParam String key ) {

        Resource resource = s3Service.downloadFile(key);

        if (resource instanceof S3Resource && resource.exists()) {
            S3Resource s3Resource = (S3Resource) resource;
            String contentType = s3Resource.contentType();

            try (InputStream inputStream = s3Resource.getInputStream()) {
                // 이미지를 바이트 배열로 읽어오기
                byte[] imageBytes = inputStream.readAllBytes();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(contentType));
                // Cache-Control 헤더를 추가하여 브라우저 캐싱을 관리.
                headers.setCacheControl("public, max-age=31536000"); // 1년 캐싱

                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);

            } catch (IOException e) {
                // 이미지 읽기 실패 처리
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
