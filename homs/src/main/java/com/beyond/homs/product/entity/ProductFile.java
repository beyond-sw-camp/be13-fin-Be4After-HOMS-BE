package com.beyond.homs.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "productfile")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductFile {
    @Id
    @JoinColumn(name = "product_id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "member_id")
    private Product product;

    @Column(name = "s3_image", length = 1024)
    private String s3Image;

    @Column(name = "s3_msds", length = 1024)
    private String s3Msds;

    @Column(name = "s3_tds1", length = 1024)
    private String s3Tds1;

    @Column(name = "s3_tds2", length = 1024)
    private String s3Tds2;

    @Column(name = "s3_property", length = 1024)
    private String s3Property;

    @Column(name = "s3_guide", length = 1024)
    private String s3Guide;
}
