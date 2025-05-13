package com.beyond.homs.notice.service;

import com.beyond.homs.notice.dto.NoticeRequestDto;
import com.beyond.homs.notice.dto.NoticeListDto;
import com.beyond.homs.notice.dto.NoticeResponseDto;
import com.beyond.homs.notice.entity.Notice;
import com.beyond.homs.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final String filePath = "notice";

    // 공지사항 목록 조회
    @Override
    public Page<NoticeListDto> getNotices(String title, Pageable pageable) {

        return noticeRepository.searchNotice(title, pageable);
    }

    // 공지사항 상세 조회
    @Override
    public NoticeResponseDto getNoticeDetail(Long noticeId){
        Notice post = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));

        return NoticeResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imagePath(post.getImagePath())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
    
    // 공지사항 등록
    @Transactional
    @Override
    public NoticeResponseDto createNotice(NoticeRequestDto requestDto)  {
        
        Notice notice = Notice.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();
        Notice saveNotice = noticeRepository.save(notice);

        return NoticeResponseDto.builder()
                .id(saveNotice.getId())
                .title(saveNotice.getTitle())
                .content(saveNotice.getContent())
                .imagePath(saveNotice.getImagePath())
                .createdAt(saveNotice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .build();
    }

    // 공지사항 수정
    @Transactional
    @Override
    public NoticeResponseDto updateNotice(Long noticeId, NoticeRequestDto requestDto) {
        Notice post = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));
        
        // 각 값이 빈값이면 이전값을 채워넣음
        if (requestDto.getTitle() == null) {
            requestDto.setTitle(post.getTitle());
        }
        if (requestDto.getContent() == null) {
            requestDto.setContent(post.getContent());
        }

        post.update(
                requestDto.getTitle(),
                requestDto.getContent());
        noticeRepository.save(post);

        return NoticeResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imagePath(post.getImagePath())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    // 공지사항 삭제
    @Transactional
    @Override
    public void deleteNotice(Long noticeId) {
        Notice post = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));

        noticeRepository.deleteById(noticeId);
    }
}
