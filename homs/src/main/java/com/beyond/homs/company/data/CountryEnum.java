package com.beyond.homs.company.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum CountryEnum {
    KOREA("KOREA"),
    USA("USA");

    @Getter
    private final String country;

    CountryEnum(String country) {
        this.country = country;
    }

    @JsonCreator
    public static CountryEnum from(String country) {
        for (CountryEnum countryEnum : CountryEnum.values()) {
            if (countryEnum.getCountry().equalsIgnoreCase(country)) {
                return countryEnum;
            }
        }
        throw new IllegalArgumentException("Invalid country: " + country);
    }

    @JsonValue
    public String getValue() {
        return country;
    }
}
