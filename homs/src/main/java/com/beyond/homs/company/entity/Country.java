package com.beyond.homs.company.entity;

import com.beyond.homs.company.data.CountryEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "country")
@Getter
@NoArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Long countryId;

    @Column(name = "country_name", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private CountryEnum countryName;

    @Builder
    public Country(CountryEnum countryName) {
        this.countryName = countryName;
    }
}
