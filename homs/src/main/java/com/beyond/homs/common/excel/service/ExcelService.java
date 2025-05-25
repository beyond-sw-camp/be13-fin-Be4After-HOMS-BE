package com.beyond.homs.common.excel.service;

import com.beyond.homs.common.excel.dto.ExcelOrderDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ExcelService  {
    List<ExcelOrderDto> excelUpload(MultipartFile file) throws IOException;

    // 엑셀 다운로드
    Resource excelDownload(Boolean list);
}
