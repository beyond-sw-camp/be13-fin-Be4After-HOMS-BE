package com.beyond.homs.notice.service;

import com.beyond.homs.notice.dto.noticeRequestDto;
import com.beyond.homs.notice.dto.NoticeListDto;
import com.beyond.homs.notice.dto.NoticeResponseDto;
import com.beyond.homs.notice.entity.Notice;
import com.beyond.homs.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    
    // 공지사항 목록 조회
    @Override
    public List<NoticeListDto> getNotices() {
        return noticeRepository.findAll().stream()
                .map(NoticeListDto::new)
                .collect(Collectors.toList());
    }
    
    // 공지사항 등록
    @Transactional
    @Override
    public NoticeResponseDto createNotice(noticeRequestDto noticeRequestDto) {

        Notice notice = Notice.builder()
                .title(noticeRequestDto.getTitle())
                .content(noticeRequestDto.getContent())
                .build();
        Notice saveNotice = noticeRepository.save(notice);

        return NoticeResponseDto.builder()
                .id(saveNotice.getId())
                .title(saveNotice.getTitle())
                .content(saveNotice.getContent())
                .createdAt(saveNotice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .build();
    }

    // 공지사항 수정
    @Transactional
    @Override
    public NoticeResponseDto updateNotice(Long noticeId, noticeRequestDto requestDto) {
        Notice post = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));

        post.update(requestDto.getTitle(), requestDto.getContent());
        noticeRepository.save(post);

        return NoticeResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    // 공지사항 삭제
    @Transactional
    @Override
    public void deleteNotice(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }
}
