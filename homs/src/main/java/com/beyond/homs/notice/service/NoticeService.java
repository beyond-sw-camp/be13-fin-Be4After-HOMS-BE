package com.beyond.homs.notice.service;

import com.beyond.homs.notice.dto.NoticeListDto;
import com.beyond.homs.notice.dto.NoticeRequestDto;
import com.beyond.homs.notice.dto.NoticeResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NoticeService {
    List<NoticeListDto> getNotices();

    // 공지사항 상세 조회
    NoticeResponseDto getNoticeDetail(Long noticeId);

    // 공지사항 등록
    @Transactional
    NoticeResponseDto createNotice(NoticeRequestDto noticeRequestDto);

    // 공지사항 수정
    @Transactional
    NoticeResponseDto updateNotice(Long noticeId, NoticeRequestDto requestDto);

    // 공지사항 삭제
    @Transactional
    void deleteNotice(Long noticeId);
}
