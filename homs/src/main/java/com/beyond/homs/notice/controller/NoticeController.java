package com.beyond.homs.notice.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.notice.dto.CreateNoticeDto;
import com.beyond.homs.notice.dto.NoticeListDto;
import com.beyond.homs.notice.dto.NoticeResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "공지사항 API", description = "공지사항 API 목록")
public interface NoticeController {
    @Operation(summary = "공지사항 생성", description = "공지사항을 생성합니다.")
    ResponseEntity<ResponseDto<NoticeResponseDto>> createNotice(
            @RequestBody CreateNoticeDto createNoticeDto
    )throws Exception;

    @Operation(summary = "공지사항 목록 조회", description = "공지사항 목록을 조회합니다.")
    ResponseEntity<ResponseDto<List<NoticeListDto>>> noticeList()throws Exception;
}

/**
 * create database homs;
 * CREATE USER `beyond`@`%` IDENTIFIED BY 'beyond';
 * GRANT ALL PRIVILEGES ON homs.* TO `beyond`@`%`;
 * FLUSH PRIVILEGES;
 */