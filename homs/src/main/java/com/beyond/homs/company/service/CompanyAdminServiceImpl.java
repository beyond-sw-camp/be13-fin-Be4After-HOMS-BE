package com.beyond.homs.company.service;

import com.beyond.homs.company.dto.CompanyDto;
import com.beyond.homs.company.dto.ResponseCompanyDto;
import com.beyond.homs.company.dto.UpdateCompanyDto;
import com.beyond.homs.company.entity.Company;
import com.beyond.homs.company.repository.CompanyRepository;
import com.beyond.homs.company.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyAdminServiceImpl implements CompanyAdminService {
    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;

    @Override
    public void grantCompany(Long companyId) {
         Company company = companyRepository.findById(companyId)
                 .orElseThrow(() -> new RuntimeException("Company not found"));

        company.setApproveStatus(true);
        company.setContinueStatus(true);
        companyRepository.saveAndFlush(company);
    }

    @Override
    public List<ResponseCompanyDto> getCompanyList() {
        return companyRepository.findAll()
                .stream()
                .map(company -> ResponseCompanyDto.fromCompany(company))
                .toList();
    }

    @Override
    public ResponseCompanyDto getCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return ResponseCompanyDto.fromCompany(company);
    }

    @Override
    public void updateTransactionStatus(Long companyId, Boolean status) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        company.setContinueStatus(status);
        companyRepository.saveAndFlush(company);
    }

    @Override
    public void updateCompany(Long companyId, CompanyDto companyDto) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        UpdateCompanyDto updateCompanyDto = new UpdateCompanyDto(
                countryRepository.findByCountryName(companyDto.country()),
                companyDto.companyName(),
                companyDto.registrationNumber(),
                companyDto.representName(),
                companyDto.representCall(),
                companyDto.representPhone(),
                companyDto.representManagerName(),
                companyDto.representManagerEmail()
        );
        company.setFromDto(updateCompanyDto);
        companyRepository.save(company);
    }
}
