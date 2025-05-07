package com.beyond.homs.notice.service;

import com.beyond.homs.notice.dto.noticeRequestDto;
import com.beyond.homs.notice.dto.NoticeListDto;
import com.beyond.homs.notice.dto.NoticeResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NoticeService {
    List<NoticeListDto> getNotices();

    // 공지사항 등록
    @Transactional
    NoticeResponseDto createNotice(noticeRequestDto noticeRequestDto);

    // 공지사항 수정
    @Transactional
    NoticeResponseDto updateNotice(Long noticeId, noticeRequestDto requestDto);

    // 공지사항 삭제
    @Transactional
    void deleteNotice(Long noticeId);
}
