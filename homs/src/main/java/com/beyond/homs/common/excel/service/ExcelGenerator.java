package com.beyond.homs.common.excel.service;

import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelGenerator {
    Workbook setAllProductList(Workbook workbook);

    // 주문한 항목 리스트
    Workbook setAllProductList(Workbook workbook, Long orderId);
}
