package com.beyond.homs.product.entity;

import com.beyond.homs.product.dto.ProductFileRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productfile")
@Getter
@NoArgsConstructor
public class ProductFile {
    @Id
    @JoinColumn(name = "product_id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "s3_image", length = 1024)
    private String s3Image;

    @Column(name = "s3_msds", length = 1024)
    private String s3Msds;

    @Column(name = "s3_tds1", length = 1024)
    private String s3Tds1;

    @Builder
    public ProductFile(Product product ,String s3Image, String s3Msds, String s3Tds1) {
        this.product = product;
        this.s3Image = s3Image;
        this.s3Msds = s3Msds;
        this.s3Tds1 = s3Tds1;
    }

    public void update(ProductFileRequestDto requestDto) {
        this.s3Image = requestDto.getS3Image();
        this.s3Msds = requestDto.getS3Msds();
        this.s3Tds1 = requestDto.getS3Tds1();
    }
}
