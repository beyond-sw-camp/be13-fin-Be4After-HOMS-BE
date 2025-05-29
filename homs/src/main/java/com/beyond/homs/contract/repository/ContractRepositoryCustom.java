package com.beyond.homs.contract.repository;

import com.beyond.homs.contract.data.ContractSearchOption;
import com.beyond.homs.contract.dto.ContractListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContractRepositoryCustom {
    Page<ContractListDto> findContractList(ContractSearchOption option, String keyword, Pageable pageable);
}
