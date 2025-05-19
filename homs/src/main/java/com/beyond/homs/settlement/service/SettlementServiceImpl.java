package com.beyond.homs.settlement.service;

import com.beyond.homs.order.entity.Order;
import com.beyond.homs.settlement.dto.SettlementRequestDto;
import com.beyond.homs.settlement.dto.SettlementResponseDto;
import com.beyond.homs.settlement.entity.Settlement;
import com.beyond.homs.settlement.repository.SettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SettlementServiceImpl implements SettlementService {
    public final SettlementRepository settlementRepository;

//    @Override
//    @Transactional
//    public  SettlementResponseDto createSettlement(SettlementRequestDto requestDto) {
//        Order order = orderService.findById(requestDto.getOrderId());
//    }

    @Override
    public List<SettlementResponseDto> getAllSettlement() {
        return settlementRepository.findAll().stream()
                .map(this::toSettlementResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SettlementResponseDto> getSettlementByUser(Long userId) {
        return settlementRepository.findAllByOrderUserUserId(userId).stream()
                .map(this::toSettlementResponse)
                .collect(Collectors.toList());
    }

    private SettlementResponseDto toSettlementResponse(Settlement settlement) {
        return new SettlementResponseDto(
                settlement.getId(),
                settlement.getOrder().getOrderCode(),
                settlement.getOrder().getUser().getCompany().getCompanyName(),
                settlement.getOrder().getDeliveryAddress().getDeliveryName(),
                settlement.getOrder().getOrderDate(),
                settlement.getSettlementDate(),
                settlement.getIsSettled().name(),
                settlement.getTaxInvoice()

        );
    }
}
