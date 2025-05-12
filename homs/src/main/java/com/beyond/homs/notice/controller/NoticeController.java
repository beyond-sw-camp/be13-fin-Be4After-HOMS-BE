package com.beyond.homs.notice.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.notice.dto.NoticeRequestDto;
import com.beyond.homs.notice.dto.NoticeListDto;
import com.beyond.homs.notice.dto.NoticeResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "공지사항 API", description = "공지사항 API 목록")
public interface NoticeController {

    @Operation(summary = "공지사항 목록 조회"
            , description = "공지사항 목록을 조회합니다.")
    ResponseEntity<ResponseDto<Page<NoticeListDto>>> noticeList(
            @RequestParam(required = false) String title,
            @PageableDefault(size = 10, page = 0) Pageable pageable);

    @Operation(summary = "공지사항 상세 조회", description = "공지사항을 상세 조회합니다.")
    ResponseEntity<ResponseDto<NoticeResponseDto>> noticeDetail(
            @PathVariable Long noticeId);

    @Operation(summary = "공지사항 생성", description = "공지사항을 생성합니다.")
    ResponseEntity<ResponseDto<NoticeResponseDto>> createNotice(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException;

    @Operation(summary = "공지사항 수정", description = "공지사항을 수정합니다.")
    ResponseEntity<ResponseDto<NoticeResponseDto>> updateNotice(
            @PathVariable Long noticeId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException;

    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제합니다.")
    ResponseEntity<ResponseDto<Void>> deleteNotice(@PathVariable Long noticeId);
}
