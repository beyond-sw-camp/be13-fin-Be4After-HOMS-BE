package com.beyond.homs.common.excel.service;

import com.beyond.homs.order.entity.OrderItem;
import com.beyond.homs.order.repository.OrderItemRepository;
import com.beyond.homs.product.dto.ProductListDto;
import com.beyond.homs.product.entity.Product;
import com.beyond.homs.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelGeneratorImpl implements ExcelGenerator {
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    private final int startRowIndex = 2; // 데이터를 채워 넣을 시작 행의 인덱스 (0-based)
    private final int startColIndex = 1; // 데이터를 채워 넣을 시작 열의 인덱스 (0-based)

    // 모든 상품 리스트
    @Override
    public Workbook setAllProductList(Workbook workbook) {

        Sheet sheet = changeName(workbook, "최소수량");

        // 데이터 가져오기
        List<Product> products = productRepository.findAll();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            Row row = sheet.getRow(startRowIndex + i);
            if (row == null) {
                // 양식에 미리 만들어진 행이 부족하면 새로 생성합니다.
                row = sheet.createRow(startRowIndex + i);
            }

            // 순번 (startColIndex + 0)
            Cell cell0 = row.getCell(startColIndex);
            if (cell0 == null) cell0 = row.createCell(startColIndex);
            cell0.setCellValue(product.getProductId()); // 상품 ID (Long 타입일 수 있으니 적절히 처리)

            // 분야,분류 (카테고리 깊이에 따라 구별)
            Cell cell1 = row.getCell(startColIndex + 1);
            if (cell1 == null) cell1 = row.createCell(startColIndex + 1);
            try {
                cell1.setCellValue(product.getCategory().getParent().getParent().getCategoryName());

                Cell cell2 = row.getCell(startColIndex + 2);
                if (cell2 == null) cell2 = row.createCell(startColIndex + 2);
                cell2.setCellValue(product.getCategory().getParent().getCategoryName());

            } catch (Exception e) {
                cell1.setCellValue(product.getCategory().getParent().getCategoryName());

                Cell cell2 = row.getCell(startColIndex + 2);
                if (cell2 == null) cell2 = row.createCell(startColIndex + 2);
                cell2.setCellValue(product.getCategory().getCategoryName());
            }

            // 제품 코드 (이름)
            Cell cell3 = row.getCell(startColIndex + 3);
            if (cell3 == null) cell3 = row.createCell(startColIndex + 3);
            cell3.setCellValue(product.getProductName());

            // 최소 수량
            Cell cell4 = row.getCell(startColIndex + 4);
            if (cell4 == null) cell4 = row.createCell(startColIndex + 4);
            cell4.setCellValue(product.getProductMinQuantity());

        }
        return workbook;
    }
    
    // 주문한 항목 리스트
    @Override
    public Workbook setAllProductList(Workbook workbook, Long orderId) {

        Sheet sheet = changeName(workbook, "주문수량");

        // 데이터 가져오기
        List<ProductListDto> products = orderItemRepository.findAllByOrder_OrderId(orderId).stream()
                .map(this::toResponseDto)
                .toList();

        for (int i = 0; i < products.size(); i++) {
            ProductListDto product = products.get(i);
            Row row = sheet.getRow(startRowIndex + i);
            if (row == null) {
                // 양식에 미리 만들어진 행이 부족하면 새로 생성합니다.
                row = sheet.createRow(startRowIndex + i);
            }

            // 순번 (startColIndex + 0)
            Cell cell0 = row.getCell(startColIndex);
            if (cell0 == null) cell0 = row.createCell(startColIndex);
            cell0.setCellValue(product.getProductId()); // 상품 ID (Long 타입일 수 있으니 적절히 처리)

            // 분야,분류 (카테고리 깊이에 따라 구별)
            Cell cell1 = row.getCell(startColIndex + 1);
            if (cell1 == null) cell1 = row.createCell(startColIndex + 1);
            cell1.setCellValue(product.getCategory().getProductDomain());

            Cell cell2 = row.getCell(startColIndex + 2);
            if (cell2 == null) cell2 = row.createCell(startColIndex + 2);
            cell2.setCellValue(product.getCategory().getProductCategory());

            // 제품 코드 (이름)
            Cell cell3 = row.getCell(startColIndex + 3);
            if (cell3 == null) cell3 = row.createCell(startColIndex + 3);
            cell3.setCellValue(product.getProductName());

            // 주문 수량
            Cell cell4 = row.getCell(startColIndex + 4);
            if (cell4 == null) cell4 = row.createCell(startColIndex + 4);
            cell4.setCellValue(product.getProductQuantity());

        }
        return workbook;
    }

    // orderItem 을 ProductListDto에 맞게 변환
    private ProductListDto toResponseDto(OrderItem orderItem) {

        Product product = orderItem.getProduct();

        return new ProductListDto(
                product.getProductId(),
                product.getProductName(),
                product.getProductMinQuantity(),
                orderItem.getQuantity(),
                product.getCategory()
        );
    }

    // 시트 이름 변경
    private Sheet changeName(Workbook workbook, String sheetName) {
        // 첫번째 시트를 가져옴
        Sheet sheet = workbook.getSheetAt(0);

        // 헤더 이름 바꾸기
        Row titleRow = sheet.getRow(0);
        if (titleRow != null) {
            Cell cell = titleRow.getCell(1);
            cell.setCellValue("상 품 목 록");
        }
        Row headerRow = sheet.getRow(1);
        if (headerRow != null) {
            Cell cell = headerRow.getCell(5);
            cell.setCellValue(sheetName);
        }

        return sheet;
    }
}
