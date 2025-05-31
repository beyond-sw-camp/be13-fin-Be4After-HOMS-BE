package com.beyond.homs.product.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductFileRequestDto {
    private Long productId;

    private String s3Image;

    private String s3Msds;

    private String s3Tds1;
}
