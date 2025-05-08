package com.beyond.homs.wms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryId implements Serializable {
    @EqualsAndHashCode.Include
    @Column(name = "warehouse_id")
    private Long warehouseId;

    @EqualsAndHashCode.Include
    @Column(name = "product_id")
    private Long productId;

    @Builder
    public InventoryId(Long warehouseId, Long productId) {
        this.warehouseId = warehouseId;
        this.productId = productId;
    }
}
