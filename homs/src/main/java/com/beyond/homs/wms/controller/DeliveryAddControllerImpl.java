package com.beyond.homs.wms.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.wms.dto.DeliveryAddRequestDto;
import com.beyond.homs.wms.dto.DeliveryAddResponseDto;
import com.beyond.homs.wms.service.DeliveryAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deliveryAdd")
public class DeliveryAddControllerImpl implements DeliveryAddController {
    private final DeliveryAddService deliveryAddService;

    // 배송지 목록 조회
    @GetMapping("/")
    @Override
    public ResponseEntity<ResponseDto<List<DeliveryAddResponseDto>>> deliveryAddList() {
        List<DeliveryAddResponseDto> deliveryList = deliveryAddService.getDeliveryList();
        return ResponseEntity.ok(
            new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "모든 배송지 목록을 불러왔습니다.",
                        deliveryList
                ));
    }

    // 배송지 추가
    @PostMapping("/create")
    @Override
    public ResponseEntity<ResponseDto<DeliveryAddResponseDto>> createDeliveryAdd(
            @RequestBody DeliveryAddRequestDto requestDto) {

        DeliveryAddResponseDto delivery = deliveryAddService.createDelivery(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ResponseDto<>(
                                HttpStatus.CREATED.value(),
                                "배송지가 추가되었습니다.",
                                delivery
                        ));
    }

    // 배송지 정보 수정
    @PutMapping("/update/{addressId}")
    @Override
    public ResponseEntity<ResponseDto<DeliveryAddResponseDto>> updateDeliveryAdd(
            @PathVariable Long addressId,
            @RequestBody DeliveryAddRequestDto requestDto) {
      DeliveryAddResponseDto delivery = deliveryAddService.updateDelivery(addressId,requestDto);

      return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            new ResponseDto<>(
                                    HttpStatus.OK.value(),
                                    "배송지 정보가 수정되었습니다.",
                                    delivery
                            ));
    }

    // 배송지 삭제
    @DeleteMapping("/delete/{addressId}")
    @Override
    public ResponseEntity<ResponseDto<Void>> deleteDeliveryAdd(
            @PathVariable Long addressId) {
        deliveryAddService.deleteDelivery(addressId);

            return ResponseEntity.ok(
                    new ResponseDto<>(
                            HttpStatus.OK.value(),
                            "배송지가 성공적으로 삭제되었습니다.",
                            null
                    ));
    }
}
