package com.beyond.homs;

import com.beyond.homs.company.data.CountryEnum;
import com.beyond.homs.company.dto.CompanyDto;
import com.beyond.homs.company.dto.ResponseCompanyDto;
import com.beyond.homs.company.entity.Company;
import com.beyond.homs.company.entity.Country;
import com.beyond.homs.company.repository.CompanyRepository;
import com.beyond.homs.company.repository.CountryRepository;
import com.beyond.homs.company.service.CompanyAdminService;
import com.beyond.homs.company.service.CompanyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CompanyAdminServiceTest {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyAdminService companyAdminService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CountryRepository countryRepository;

    @AfterEach
    void afterEach() {
        companyRepository.deleteAll();
    }

    @Test
    public void countryRepositoryTest() {
        // Test the country repository
        List<Country> countries = countryRepository.findAll();
        assertThat(countries).isNotEmpty();

        Country korea = countryRepository.findByCountryName(CountryEnum.KOREA);
        assertThat(korea).isNotNull();
        assertThat(korea.getCountryName().getValue()).isEqualTo(CountryEnum.KOREA.getValue());
    }

    @Test
    public void enrollmentCompanyTest() {
        CompanyDto companyDto = new CompanyDto(
                CountryEnum.KOREA,
                "Test Company",
                "123456789",
                "Adrian",
                "010-1234-5678",
                "010-1234-5678",
                "Adrian",
                "adr@abc.com"
        );

        companyService.enrollmentCompany(companyDto);
        List<Company> companies = companyRepository.findAll();
        assertThat(companies).hasSize(1);
        assertThat(companies.getFirst().getCompanyName()).isEqualTo("Test Company");
    }

    @Test
    public void grantCompanyTest() {
        CompanyDto companyDto = new CompanyDto(
                CountryEnum.KOREA,
                "Test Company",
                "123456789",
                "Adrian",
                "010-1234-5678",
                "010-1234-5678",
                "Adrian",
                "adr@abc.com"
        );
        companyService.enrollmentCompany(companyDto);
        List<ResponseCompanyDto> companies = companyAdminService.getCompanyList();
        assertThat(companies).hasSize(1);
        Long companyId = companies.getFirst().companyId();
        companyAdminService.grantCompany(companyId);

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        assertThat(company.isApproveStatus()).isTrue();
    }

    @Test
    public void updateTransactionStatusTest() {
        CompanyDto companyDto = new CompanyDto(
                CountryEnum.KOREA,
                "Test Company",
                "123456789",
                "Adrian",
                "010-1234-5678",
                "010-1234-5678",
                "Adrian",
                "adr@abc.com"
        );
        companyService.enrollmentCompany(companyDto);
        List<ResponseCompanyDto> companies = companyAdminService.getCompanyList();

        Long companyId = companies.getFirst().companyId();
        companyAdminService.updateTransactionStatus(companyId, true);
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        assertThat(company.isContinueStatus()).isTrue();
    }

    @Test
    public void updateCompanyTest() {
        CompanyDto companyDto = new CompanyDto(
                CountryEnum.KOREA,
                "Test Company",
                "123456789",
                "Adrian",
                "010-1234-5678",
                "010-1234-5678",
                "Adrian",
                "adr@abc.com"
        );

        companyService.enrollmentCompany(companyDto);
        List<ResponseCompanyDto> companies = companyAdminService.getCompanyList();
        Long companyId = companies.getFirst().companyId();

        CompanyDto updateCompanyDto = new CompanyDto(
                CountryEnum.KOREA,
                "Updated Company",
                "987654321",
                "Adrian",
                "010-9876-5432",
                "010-9876-5432",
                "Adrian",
                "adr@abc.com"
        );
        companyAdminService.updateCompany(companyId, updateCompanyDto);
        Company updatedCompany = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        assertThat(updatedCompany.getCompanyName()).isEqualTo("Updated Company");
        assertThat(updatedCompany.getRegistrationNumber()).isEqualTo("987654321");
        assertThat(updatedCompany.getRepresentCall()).isEqualTo("010-9876-5432");
    }
}
