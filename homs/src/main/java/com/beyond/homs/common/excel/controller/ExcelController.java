package com.beyond.homs.common.excel.controller;

import com.beyond.homs.common.excel.dto.ExcelOrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Excel API", description = "Excel API 목록")
public interface ExcelController {

    @Operation(summary = "엑셀 파일 업로드", description = "엑셀 파일을 업로드하고 파싱합니다.")
    List<ExcelOrderDto> uploadExcel(@RequestParam("file") MultipartFile file) throws IOException;

    @Operation(summary = "엑셀 파일 다운로드", description = "엑셀 파일을 다운로드 합니다.")
    ResponseEntity<Resource> downloadExcel(
            @RequestParam String type,
            @RequestParam(required = false) Long orderId);
}
