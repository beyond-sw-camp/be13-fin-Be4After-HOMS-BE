package com.beyond.homs.common.excel.controller;

import com.beyond.homs.common.excel.data.ExcelTypeEnum;
import com.beyond.homs.common.excel.service.ExcelService;
import com.beyond.homs.order.dto.OrderItemResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping( "api/v1/excel")
public class ExcelControllerImpl implements ExcelController {

    private final ExcelService excelService;

    // 엑셀 업로드
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public List<OrderItemResponseDto> uploadExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long orderId) throws IOException {
        return excelService.excelUpload(file,orderId);
    }

    // 엑셀 다운로드
    @GetMapping("/download")
    @Override
    public ResponseEntity<Resource> downloadExcel(
            @RequestParam ExcelTypeEnum type,
            @RequestParam(required = false) Long orderId) throws UnsupportedEncodingException {
        Resource file = excelService.excelDownload(type, orderId);

        String baseName = ""; // 기본 이름

        // orderId 유무에 따른 접두사 결정 로직
        switch (type) {
            case TEMPLATE -> baseName = "발주_신청서";
            case ALL-> baseName = "전체_상품_목록";
            case ORDER -> baseName = "주문_상품_목록";
        }

        // 최종 파일명 생성: 접두사 + 기본 이름 + 현재 날짜/시간
        String finalFileName = baseName + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx";

        // RFC 5987 용으로 인코딩된 파일명
        String encodedFileNameRfc5987 = URLEncoder.encode(finalFileName, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20"); // RFC 5987에서는 공백을 %20으로 인코딩 (URLEncoder 기본 +는 %2B)


        // Content-Disposition 헤더 생성:
        // filename="" 부분에는 ASCII 문자만 허용되므로, 한글 파일명을 그대로 넣지 않거나,
        // 이 부분도 인코딩된 안전한 값(예: download.xlsx 또는 ASCII로 변환된 이름)을 사용해야 합니다.
        String contentDispositionHeader = "attachment; ";

        contentDispositionHeader += "filename*=UTF-8''" + encodedFileNameRfc5987;

        return ResponseEntity.ok()
                // Content-Disposition 헤더에 RFC 5987 인코딩된 파일명 사용
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDispositionHeader)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) // 올바른 MIME 타입
                .body(file);
    }
}
