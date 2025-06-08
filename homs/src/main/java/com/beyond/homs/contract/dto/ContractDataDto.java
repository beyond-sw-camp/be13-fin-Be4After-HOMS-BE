package com.beyond.homs.contract.dto;

import com.beyond.homs.company.entity.Company;
import com.beyond.homs.product.entity.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ContractDataDto {
    private final List<CompanyDto> companyList;

    private final List<ProductDto> productList;

    // Company 정보를 담을 DTO 내부 클래스
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CompanyDto {
        private Long id;
        private String companyName;
        private String representManagerName;
    }

    // Product 정보를 담을 DTO 내부 클래스
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ProductDto {
        private Long id;
        private String name;
        private String category;
    }
}
