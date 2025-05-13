package com.beyond.homs.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class WarehouseRequestDto {

    private String warehouseName;

    private String location;
}
