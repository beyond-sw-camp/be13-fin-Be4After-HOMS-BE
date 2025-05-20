package com.beyond.homs.settlement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SettlementCompanyInfoDto {
    private String companyName;
    private String registrationNumber;
    private String representName;
    private String address;
}
