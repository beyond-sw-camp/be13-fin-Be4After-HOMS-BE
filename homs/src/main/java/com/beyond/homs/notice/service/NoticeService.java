package com.beyond.homs.notice.service;

import com.beyond.homs.notice.dto.CreateNoticeDto;
import com.beyond.homs.notice.dto.NoticeListDto;
import com.beyond.homs.notice.dto.NoticeResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NoticeService {
    List<NoticeListDto> getNotices();

    // 공지사항 등록
    @Transactional
    NoticeResponseDto createNotice(CreateNoticeDto createNoticeDto);

    // 공지사항 삭제
    @Transactional
    void deleteNotice(Long noticeId);
}
