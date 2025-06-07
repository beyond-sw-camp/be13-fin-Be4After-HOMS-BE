package com.beyond.homs.order.dto;

import com.beyond.homs.order.entity.Order;
import com.beyond.homs.product.dto.ProductListDto;
import com.beyond.homs.product.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
    public class OrderItemResponseDto {
    private final OrderResponseDto order;

    private final ProductListDto product;

     private final Long quantity;
}
