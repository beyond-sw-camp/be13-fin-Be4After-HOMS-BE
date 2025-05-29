package com.beyond.homs.contract.service;

import com.beyond.homs.contract.data.ContractSearchOption;
import com.beyond.homs.contract.dto.ContractListDto;
import com.beyond.homs.contract.dto.ContractRequestDto;
import com.beyond.homs.contract.dto.ContractResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ContractService {
    ContractResponseDto createContract(@Valid ContractRequestDto contractRequestDto);

    Page<ContractListDto> getContracts(ContractSearchOption option, String keyword, Pageable pageable);

    ContractResponseDto getContractDetail(Long contractDetail);
}
