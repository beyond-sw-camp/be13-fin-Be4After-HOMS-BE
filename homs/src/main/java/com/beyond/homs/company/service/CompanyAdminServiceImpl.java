package com.beyond.homs.company.service;

import com.beyond.homs.company.dto.CompanyDto;
import com.beyond.homs.company.dto.ResponseCompanyDto;
import com.beyond.homs.company.dto.UpdateCompanyDto;
import com.beyond.homs.company.entity.Company;
import com.beyond.homs.company.repository.CompanyRepository;
import com.beyond.homs.company.repository.CountryRepository;
import com.beyond.homs.wms.entity.DeliveryAddress;
import com.beyond.homs.wms.repository.DeliveryAddRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyAdminServiceImpl implements CompanyAdminService {
    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;
    private final DeliveryAddRepository deliveryAddRepository;

    @Override
    public void grantCompany(Long companyId, Boolean status) {
         Company company = companyRepository.findById(companyId)
                 .orElseThrow(() -> new RuntimeException("Company not found"));

        System.out.println("기존 승인 상태: " + company.isApproveStatus());
        System.out.println("변경할 승인 상태: " + status);

        company.changeApproveStatus(status);
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
        DeliveryAddress address = deliveryAddRepository.findFirstByCompany_CompanyId(companyId)
                .orElse(null);
        String addressName = address != null ? address.getStreetAddress() : null;

        return ResponseCompanyDto.fromCompany(company,addressName);
    }

    @Override
    public void updateTransactionStatus(Long companyId, Boolean status) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        company.changeContinueStatus(status);

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
                companyDto.representManagerEmail(),
                companyDto.continueStatus(),
                companyDto.approveStatus()
        );
        company.setFromDto(updateCompanyDto);
        companyRepository.save(company);
    }
}
