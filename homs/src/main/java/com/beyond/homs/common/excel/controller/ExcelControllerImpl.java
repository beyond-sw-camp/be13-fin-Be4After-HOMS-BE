package com.beyond.homs.common.excel.controller;

import com.beyond.homs.common.excel.dto.ExcelOrderDto;
import com.beyond.homs.common.excel.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/excel")
public class ExcelControllerImpl implements ExcelController {

    private final ExcelService excelService;

    // 엑셀 업로드
    @PostMapping(value ="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public List<ExcelOrderDto> uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return excelService.excelUpload(file);
    }

    // 엑셀 다운로드
    @GetMapping("/download")
    @Override
    public ResponseEntity<Resource> downloadExcel(
            @RequestParam String type,
            @RequestParam(required = false) Long orderId) {
        Resource file = excelService.excelDownload(type, orderId);

        String baseName = "상품_목록"; // 기본 이름
        String prefix = "";

        // orderId 유무에 따른 접두사 결정 로직
        if (orderId == null) {
            prefix = "전체_";
        } else {
            prefix = "주문_";
        }

        // 최종 파일명 생성: 접두사 + 기본 이름 + 현재 날짜/시간
        String finalFileName = prefix + baseName + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        // 파일명 URL 인코딩 (한글 및 특수문자 깨짐 방지)
        String encodedFileName;
        try {
            // URLEncoder는 공백을 '+'로 인코딩하는데, 일부 브라우저가 이를 '%20'으로 더 잘 인식하므로 변환합니다.
            encodedFileName = URLEncoder.encode(finalFileName, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
        } catch (Exception e) {
            // 인코딩 실패 시 대비 (거의 발생하지 않음)
            encodedFileName = "download"; // 대체 파일명
        }

        return ResponseEntity.ok()
                // Content-Disposition 헤더에 인코딩된 파일명 사용. .xlsx 확장자도 명시적으로 붙여줍니다.
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + ".xlsx\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) // 올바른 MIME 타입
                .body(file);
    }
}
