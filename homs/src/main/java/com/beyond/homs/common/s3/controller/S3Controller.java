package com.beyond.homs.common.s3.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "파일관리 API", description = "S3를 활용한 파일관리 API 목록")
public interface S3Controller {
    @Operation(summary = "파일 업로드", description = "S3에 파일을 업로드합니다.")
    ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("key") String key);

    @Operation(summary = "파일 다운로드", description = "S3에 파일을 다운로드합니다.")
    ResponseEntity<Resource> downloadFile(
            @RequestParam("key") String key);

    @Operation(summary = "파일 삭제", description = "S3에 파일을 삭제합니다.")
    ResponseEntity<String> deleteFile(@RequestParam("key") String key);

    @Operation(summary = "이미지 출력", description = "S3에서 이미지 받아와서 출력합니다.")
    ResponseEntity<byte[]> streamImage(
            @RequestParam String key) throws IOException;

    @Operation(summary = "상품관리 다중 파일 업로드", description = "상품관리 페이지에서 사용되는 다중 파일 업로드입니다.")
    ResponseEntity<?> uploadMultipleFiles(
            // @RequestParam의 "value"는 프론트엔드의 FormData.append("name", file)의 "name"과 일치해야 합니다.
            @PathVariable Long productId,
            @RequestParam(value = "s3Image", required = false) MultipartFile s3Image,
            @RequestParam(value = "s3Msds", required = false) MultipartFile s3Msds,
            @RequestParam(value = "s3Tds1", required = false) MultipartFile s3Tds1);
}