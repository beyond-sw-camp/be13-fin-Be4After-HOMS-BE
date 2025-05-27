package com.beyond.homs.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ordertemplate")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    private int templateId;

    @Column(name = "s3_order_template")
    private String s3OrderTemplate;

    @Builder
    public Template(String s3OrderTemplate) {
        this.s3OrderTemplate = s3OrderTemplate;
    }
}
