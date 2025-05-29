package com.beyond.homs.contract.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.contract.data.ContractSearchOption;
import com.beyond.homs.contract.dto.ContractListDto;
import com.beyond.homs.contract.dto.ContractRequestDto;
import com.beyond.homs.contract.dto.ContractResponseDto;
import com.beyond.homs.contract.service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contract")
public class ContractControllerImpl implements ContractController {
    private final ContractService contractService;

    @PostMapping("/")
    @Override
    public ResponseEntity<ResponseDto<ContractResponseDto>> createContract(
            @Valid @RequestBody ContractRequestDto contractRequestDto) {
        ContractResponseDto created = contractService.createContract(contractRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto<>(
                        HttpStatus.CREATED.value(),
                        "계약이 성공적으로 생성되었습니다.",
                        created
                ));
    }

    @GetMapping("/")
    @Override
    public ResponseEntity<ResponseDto<Page<ContractListDto>>> contractList(
            @RequestParam(required = false) ContractSearchOption option,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<ContractListDto> page = contractService.getContracts(option, keyword, pageable);
        return ResponseEntity.ok(
                new ResponseDto<>(HttpStatus.OK.value(), "계약 목록 조회 성공", page)
        );
    }

    @GetMapping("/{contractId}")
    @Override
    public ResponseEntity<ResponseDto<ContractResponseDto>> contractDetail(
            @PathVariable Long contractId) {

        ContractResponseDto detail = contractService.getContractDetail(contractId);
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "계약 상세 정보를 불러왔습니다.",
                detail
        ));
    }
}
