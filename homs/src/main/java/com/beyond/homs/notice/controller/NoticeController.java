package com.beyond.homs.notice.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.notice.dto.noticeRequestDto;
import com.beyond.homs.notice.dto.NoticeListDto;
import com.beyond.homs.notice.dto.NoticeResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "공지사항 API", description = "공지사항 API 목록")
public interface NoticeController {

    @Operation(summary = "공지사항 목록 조회", description = "공지사항 목록을 조회합니다.")
    ResponseEntity<ResponseDto<List<NoticeListDto>>> noticeList()throws Exception;

    @Operation(summary = "공지사항 상세 조회", description = "공지사항을 상세 조회합니다.")
    ResponseEntity<ResponseDto<NoticeResponseDto>> noticeDetail(
            @PathVariable Long noticeId
    )throws Exception;

    @Operation(summary = "공지사항 생성", description = "공지사항을 생성합니다.")
    ResponseEntity<ResponseDto<NoticeResponseDto>> createNotice(
            @RequestBody noticeRequestDto noticeRequestDto
    )throws Exception;

    @Operation(summary = "공지사항 수정", description = "공지사항을 수정합니다.")
    ResponseEntity<ResponseDto<NoticeResponseDto>> updateNotice(
            @PathVariable Long noticeId,
            @RequestBody noticeRequestDto noticeRequestDto
    )throws Exception;

    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제합니다.")
    ResponseEntity<ResponseDto<Void>> deleteNotice(@PathVariable Long noticeId)throws Exception;
}

/**
 * create database homs;
 * CREATE USER `beyond`@`%` IDENTIFIED BY 'beyond';
 * GRANT ALL PRIVILEGES ON homs.* TO `beyond`@`%`;
 * FLUSH PRIVILEGES;
 */