package com.beyond.homs.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductFileResponseDto {
    private Long id;

    private String s3Image;

    private String s3Msds;

    private String s3Tds1;

}
