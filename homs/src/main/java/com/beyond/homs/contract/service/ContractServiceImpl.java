package com.beyond.homs.contract.service;

import com.beyond.homs.common.exception.exceptions.CustomException;
import com.beyond.homs.common.exception.messages.ExceptionMessage;
import com.beyond.homs.company.entity.Company;
import com.beyond.homs.company.repository.CompanyRepository;
import com.beyond.homs.contract.data.ContractSearchOption;
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
        Company company = companyRepository.findById(contractRequestDto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("회사 정보가 없습니다. id=" + contractRequestDto.getCompanyId()));
        Product product = productRepository.findById(contractRequestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품 정보가 없습니다. id=" + contractRequestDto.getProductId()));

        Contract contract = Contract.builder()
                .company(company)
                .product(product)
                .contractStartAt(contractRequestDto.getContractStartAt())
                .contractStopAt(contractRequestDto.getContractStopAt())
                .build();

        contractRepository.save(contract);
        return toResponseDto(contract);
    }

    @Override
    public Page<ContractListDto> getContracts(ContractSearchOption option, String keyword, Pageable pageable) {
        Page<ContractListDto> searchResult = contractRepository.findContractList(option, keyword, pageable);

        // 검색결과가 없는 경우 예외 처리
        if (searchResult.isEmpty()) {
            throw new CustomException(ExceptionMessage.INVALID_SEARCH_KEYWORD);
        }

        return searchResult;
    }

    @Override
    public ContractResponseDto getContractDetail(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new IllegalArgumentException("해당 계약이 없습니다. id=" + contractId));
        return toResponseDto(contract);
    }

    private ContractResponseDto toResponseDto(Contract contract) {
        return new ContractResponseDto(
                contract.getContractId(),
                contract.getCompany().getCompanyName(),
                contract.getProduct().getProductName(),
                contract.getCompany().getRepresentManagerName(),
                contract.getContractStartAt(),
                contract.getContractStopAt(),
                contract.getProduct().getCategory().getCategoryName()
        );
    }
}
