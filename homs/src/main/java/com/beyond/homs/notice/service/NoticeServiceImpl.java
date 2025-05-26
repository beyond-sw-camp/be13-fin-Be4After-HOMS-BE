package com.beyond.homs.notice.service;

import com.beyond.homs.common.exception.exceptions.CustomException;
import com.beyond.homs.common.exception.messages.ExceptionMessage;
import com.beyond.homs.common.util.SecurityUtil;
import com.beyond.homs.notice.dto.NoticeRequestDto;
import com.beyond.homs.notice.dto.NoticeListDto;
import com.beyond.homs.notice.dto.NoticeResponseDto;
import com.beyond.homs.notice.entity.Notice;
import com.beyond.homs.notice.repository.NoticeRepository;
import com.beyond.homs.user.data.UserRole;
import com.beyond.homs.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;

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

        // 현재 유저의 권한을 가져옴
        UserRole role = SecurityUtil.getCurrentUserRole();

        if(role != UserRole.ROLE_ADMIN){
            throw new CustomException(ExceptionMessage.NOT_PERMISSION_USER);
        }


        Notice notice = Notice.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .imagePath(requestDto.getImagePath())
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
                .orElseThrow(() -> new CustomException(ExceptionMessage.POST_NOT_FOUND));

        // 현재 유저의 권한을 가져옴
        UserRole role = SecurityUtil.getCurrentUserRole();
        if(role != UserRole.ROLE_ADMIN){
            throw new CustomException(ExceptionMessage.NOT_PERMISSION_USER);
        }
        
        // 각 값이 빈값이면 이전값을 채워넣음
        if (requestDto.getTitle() == null) {
            requestDto.setTitle(post.getTitle());
        }
        if (requestDto.getContent() == null) {
            requestDto.setContent(post.getContent());
        }
        if (requestDto.getImagePath() == null) {
            requestDto.setImagePath(post.getImagePath());
        }

        post.update(
                requestDto.getTitle(),
                requestDto.getContent(),
                requestDto.getImagePath());
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
        // 현재 유저의 권한을 가져옴
        UserRole role = SecurityUtil.getCurrentUserRole();
        if(role != UserRole.ROLE_ADMIN){
            throw new CustomException(ExceptionMessage.NOT_PERMISSION_USER);
        }

        noticeRepository.findById(noticeId)
                .orElseThrow(() -> new CustomException(ExceptionMessage.POST_NOT_FOUND));
        noticeRepository.deleteById(noticeId);
    }
}
