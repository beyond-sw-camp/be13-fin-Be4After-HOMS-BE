package com.beyond.homs.company.repository;

import com.beyond.homs.company.data.CountryEnum;
import com.beyond.homs.company.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByCountryName(CountryEnum countryName);
}
