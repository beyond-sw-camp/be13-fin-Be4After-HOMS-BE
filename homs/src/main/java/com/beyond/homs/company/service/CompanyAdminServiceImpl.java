package com.beyond.homs.company.service;

import com.beyond.homs.company.dto.CompanyDto;
import com.beyond.homs.company.dto.ResponseCompanyDto;
import com.beyond.homs.company.entity.Company;
import com.beyond.homs.company.repository.CompanyRepository;
import com.beyond.homs.company.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyAdminServiceImpl implements CompanyAdminService {
    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;

    @Override
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
                .country(countryRepository.findByCountryName(enrollmentCompanyDto.country().getValue()))
                .build();
        companyRepository.save(company);
    }

    @Override
    public void grantCompany(Long companyId) {
         Company company = companyRepository.findById(companyId)
                 .orElseThrow(() -> new RuntimeException("Company not found"));

        company.setApproveStatus(true);
        companyRepository.save(company);
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
        company.setApproveStatus(status);
        companyRepository.save(company);
    }

    @Override
    public void updateCompany(Long companyId, CompanyDto updateCompanyDto) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        company.setFromDto(updateCompanyDto);
        companyRepository.save(company);
    }
}
