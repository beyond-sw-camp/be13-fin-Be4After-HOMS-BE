package com.beyond.homs.wms.entity;

import com.beyond.homs.company.entity.Company;
import com.beyond.homs.wms.dto.DeliveryAddRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "delivery_name")
    private String deliveryName;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "detailed_address")
    private String detailedAddress;

    @Column(name = "reference")
    private String reference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Builder
    public DeliveryAddress(String deliveryName, String postalCode, String streetAddress, String detailedAddress, String reference, Company company)
    {
        this.deliveryName = deliveryName;
        this.postalCode = postalCode;
        this.streetAddress = streetAddress;
        this.detailedAddress = detailedAddress;
        this.reference = reference;
        this.company = company;
    }

    public void updateDeliveryAddress(DeliveryAddRequestDto requestDto, Company company) {
        this.deliveryName = requestDto.getDeliveryName();
        this.postalCode = requestDto.getPostalCode();
        this.streetAddress = requestDto.getStreetAddress();
        this.detailedAddress = requestDto.getDetailedAddress();
        this.reference = requestDto.getReference();
        this.company = company;
    }
}
