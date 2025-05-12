package com.beyond.homs.notice.service;

import com.beyond.homs.notice.dto.NoticeListDto;
import com.beyond.homs.notice.dto.NoticeRequestDto;
import com.beyond.homs.notice.dto.NoticeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

public interface NoticeService {

    // 공지사항 목록 조회
    Page<NoticeListDto> getNotices(String title, Pageable pageable);

    // 공지사항 상세 조회
    NoticeResponseDto getNoticeDetail(Long noticeId);

    // 공지사항 등록
    @Transactional
    NoticeResponseDto createNotice(NoticeRequestDto requestDto) throws IOException;

    // 공지사항 수정
    @Transactional
    NoticeResponseDto updateNotice(Long noticeId, NoticeRequestDto requestDto) throws IOException;

    // 공지사항 삭제
    @Transactional
    void deleteNotice(Long noticeId);
}
