package com.beyond.homs.common.S3.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.product.dto.ProductListDto;
import com.beyond.homs.product.dto.ProductRequestDto;
import com.beyond.homs.product.dto.ProductResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "파일관리 API (샘플)", description = "S3를 활용한 파일관리 API 목록")
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
            @RequestParam String key);
}