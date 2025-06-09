package com.beyond.homs.company.service;

import com.beyond.homs.company.dto.CompanyDto;
import com.beyond.homs.company.dto.ResponseCompanyDto;
import com.beyond.homs.company.entity.Company;
import com.beyond.homs.company.repository.CompanyRepository;
import com.beyond.homs.company.repository.CountryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;

    @Override
    @Transactional
    public void enrollmentCompany(CompanyDto enrollmentCompanyDto) {
        Company company = Company.builder()
                .companyName(enrollmentCompanyDto.companyName())
                .registrationNumber(enrollmentCompanyDto.registrationNumber())
                .representName(enrollmentCompanyDto.representName())
                .representCall(enrollmentCompanyDto.representCall())
                .representPhone(enrollmentCompanyDto.representPhone())
                .representManagerName(enrollmentCompanyDto.representManagerName())
                .representManagerEmail(enrollmentCompanyDto.representManagerEmail())
                .continueStatus(false)
                .approveStatus(false)
                .country(countryRepository.findByCountryName(enrollmentCompanyDto.country()))
                .build();
        companyRepository.save(company);
    }

    @Override
    public ResponseCompanyDto getCompany(Long companyId) {
        // company id 비교 로직 추가
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return ResponseCompanyDto.fromCompany(company);
    }
}
