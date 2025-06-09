package com.beyond.homs.contract.service;

import com.beyond.homs.common.exception.exceptions.CustomException;
import com.beyond.homs.common.exception.messages.ExceptionMessage;
import com.beyond.homs.company.entity.Company;
import com.beyond.homs.company.repository.CompanyRepository;
import com.beyond.homs.contract.data.ContractSearchOption;
import com.beyond.homs.contract.dto.ContractDataDto;
import com.beyond.homs.contract.dto.ContractListDto;
import com.beyond.homs.contract.dto.ContractRequestDto;
import com.beyond.homs.contract.dto.ContractResponseDto;
import com.beyond.homs.contract.entity.Contract;
import com.beyond.homs.contract.repository.ContractRepository;
import com.beyond.homs.product.entity.Product;
import com.beyond.homs.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final CompanyRepository companyRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ContractResponseDto createContract(ContractRequestDto contractRequestDto) {
        // 1) companyId로 조회
        Company company = companyRepository.findByCompanyId(contractRequestDto.getCompanyId());

        // 2) productId로 조회
        Product product = productRepository.findByProductId(contractRequestDto.getProductId());

        // 3) Contract 엔티티 빌드 및 저장
        Contract contract = Contract.builder()
                .company(company)
                .product(product)
                .contractStartAt(contractRequestDto.getContractStartAt())
                .contractStopAt(contractRequestDto.getContractStopAt())
                .build();

        contractRepository.save(contract);

        // 4) 저장된 엔티티를 ResponseDto 로 변환
        return toResponseDto(contract);
    }

    @Override
    public Page<ContractListDto> getContracts(ContractSearchOption option, String keyword, Pageable pageable) {
        Page<ContractListDto> searchResult = contractRepository.findContractList(option, keyword, pageable);

        return searchResult;
    }

    @Override
    public ContractResponseDto getContractDetail(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new IllegalArgumentException("해당 계약이 없습니다. id=" + contractId));
        return toResponseDto(contract);
    }

    @Override
    public ContractDataDto getContractData(){
        List<Company> companyList = companyRepository.findAll();
        List<Product> productList = productRepository.findAll();

        // Company 엔티티를 ContractDataDto.CompanyDto로 변환
        List<ContractDataDto.CompanyDto> companyDtoList = companyList.stream()
                .map(company -> new ContractDataDto.CompanyDto(company.getCompanyId(), company.getCompanyName(),company.getRepresentManagerName()))
                .toList();

        // Product 엔티티를 ContractDataDto.ProductDto로 변환
        List<ContractDataDto.ProductDto> productDtoList = productList.stream()
                .map(product -> new ContractDataDto.ProductDto(
                        product.getProductId(),
                        product.getProductName(),
                        product.getCategory().getParent().getCategoryName()))
                .toList();

        return new ContractDataDto(companyDtoList, productDtoList);
    }

    private ContractResponseDto toResponseDto(Contract contract) {
        return new ContractResponseDto(
                contract.getContractId(),
                contract.getCompany().getCompanyName(),
                contract.getProduct().getProductName(),
                contract.getCompany().getRepresentManagerName(),
                contract.getContractStartAt(),
                contract.getContractStopAt(),
                contract.getProduct().getCategory().getParent().getCategoryName()
        );
    }
}
