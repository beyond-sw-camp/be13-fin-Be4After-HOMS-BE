package com.beyond.homs.common.excel.service;

import com.beyond.homs.common.excel.dto.ExcelOrderDto;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {

    private final ExcelGenerator excelGenerator;
    
    @Value("${excel.template.path}")
    private String EXCEL_TEMPLATE_PATH;

    // 엑셀 업로드
    @Override
    public List<ExcelOrderDto> excelUpload(MultipartFile file) throws IOException {

        DataFormatter dataFormatter = new DataFormatter();

        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("파일을 선택해주세요");
            }

            // 파일 확장자 검증
            String fileName = file.getOriginalFilename();
            if (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls")) {
                throw new IllegalArgumentException("Excel 파일만 업로드 가능합니다.");
            }

            List<ExcelOrderDto> orderDto = new ArrayList<>();

            // Excel 파일 처리
            // try-with-resources 구문으로 변경하여 Workbook이 자동으로 닫히도록 합니다.
            try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
                Sheet sheet = workbook.getSheetAt(0);
                // sheet.getLastRowNum()을 사용하여 실제 마지막 행 인덱스 가져오기
                int lastRowNum = sheet.getLastRowNum();

                // 헤더  행 읽기
                Row headerRow = sheet.getRow(1);
                if (headerRow != null) {
                    for (Cell cell : headerRow) {
                        System.out.println(cell.getStringCellValue());
                    }
                }

                // 데이터 행 읽기
                for (int i = 2; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);

                    // 빈 행이거나 데이터가 없는 행일 경우 row.getCell() 호출 시 NullPointerException 발생
                    if (row == null || isRowEmpty(row, dataFormatter)) {
                        System.out.println("경고: " + i + "번째 행이 비어있거나 존재하지 않아 건너띔.");
                        continue; // 해당 행 건너뛰기
                    }

                    // 행 들은 리스트 객체로 구성
                    orderDto.add(ExcelOrderDto.builder()
                            .productId((long) row.getCell(1).getNumericCellValue()) // 순번
                            .productDomain(row.getCell(2).getStringCellValue())
                            .productCategory(row.getCell(3).getStringCellValue())
                            .productName(dataFormatter.formatCellValue(row.getCell(4)))
                            .productQuantity((long) row.getCell(5).getNumericCellValue())
                            .build());
                }

                return orderDto;
            }
        } catch (IOException e) {
            throw new IOException("파일 처리 중 오류가 발생했습니다: " + e.getMessage(), e);
        } catch (Exception e) {
            // 예상치 못한 모든 예외
            throw new RuntimeException("엑셀 데이터 파싱 중 알 수 없는 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    // 엑셀 템플릿 다운로드
    @Override
    public Resource excelDownload(String type, Long orderId) {
        Workbook workbook;

        try (InputStream templateStream = new ClassPathResource(EXCEL_TEMPLATE_PATH).getInputStream()) {
            // 템플릿 엑셀 파일을 로드합니다.
            workbook = WorkbookFactory.create(templateStream);
        } catch (IOException e) {
            // 템플릿 파일이 없거나 읽을 수 없을 때 예외 처리
            throw new RuntimeException("엑셀 템플릿 파일을 로드할 수 없습니다: " + EXCEL_TEMPLATE_PATH, e);
        }
        
        // 데이터 저장
        if (type != null) {
            if(orderId != null) {
                workbook = excelGenerator.setAllProductList(workbook,orderId);
            } else{
                workbook = excelGenerator.setAllProductList(workbook);
            }
        }

        // --- 파일 생성 및 반환 로직 (기존과 동일) ---
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
            workbook.close(); // workbook 닫기 (try-with-resources 사용 시 자동으로 닫힘)
        } catch (IOException e) {
            throw new RuntimeException("엑셀 파일 생성 중 오류가 발생했습니다.", e);
        }

        return new ByteArrayResource(outputStream.toByteArray());
    }

    // 빈 행 확인
    private boolean isRowEmpty(Row row, DataFormatter dataFormatter) {
        if (row == null) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && !dataFormatter.formatCellValue(cell).trim().isEmpty()) {
                return false; // 데이터가 있는 셀을 찾았으므로 행은 비어있지 않음
            }
        }
        return true; // 모든 셀이 비어있거나 데이터가 없음
    }

}
