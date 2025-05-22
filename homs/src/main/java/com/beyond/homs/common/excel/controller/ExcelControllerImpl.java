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
    @GetMapping("/download/{list}")
    @Override
    public ResponseEntity<Resource> downloadExcel(@PathVariable Boolean list){
        Resource file = excelService.excelDownload(list);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"data.xlsx\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

}
