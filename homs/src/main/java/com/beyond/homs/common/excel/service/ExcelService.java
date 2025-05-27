package com.beyond.homs.common.excel.service;

import com.beyond.homs.common.excel.data.ExcelTypeEnum;
import com.beyond.homs.order.dto.OrderItemResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ExcelService  {
    // 엑셀 업로드
    List<OrderItemResponseDto> excelUpload(MultipartFile file, Long orderId) throws IOException;

    // 엑셀 다운로드
    Resource excelDownload(ExcelTypeEnum type, Long orderId);
}
