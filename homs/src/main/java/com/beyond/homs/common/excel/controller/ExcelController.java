package com.beyond.homs.common.excel.controller;

import com.beyond.homs.common.excel.data.ExcelTypeEnum;
import com.beyond.homs.order.dto.OrderItemResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Tag(name = "Excel API", description = "Excel API 목록")
public interface ExcelController {

    @Operation(summary = "엑셀 파일 업로드", description = "엑셀 파일을 업로드하고 파싱합니다.")
    List<OrderItemResponseDto> uploadExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long orderId) throws IOException;

    @Operation(summary = "엑셀 파일 다운로드", description = "엑셀 파일을 다운로드 합니다.")
    ResponseEntity<Resource> downloadExcel(
            @RequestParam ExcelTypeEnum type,
            @RequestParam(required = false) Long orderId) throws UnsupportedEncodingException;
}
