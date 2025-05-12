package com.beyond.homs.notice.service;

import com.beyond.homs.common.service.FileStorageService;
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
    private final FileStorageService fileStorageService;

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
                .image_path(post.getImage_path())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
    
    // 공지사항 등록
    @Transactional
    @Override
    public NoticeResponseDto createNotice(NoticeRequestDto requestDto) {

        // String filePath = null;
        //
        // // 파일 업로드가 되었다면
        // if (file != null) {
        //     try {
        //         filePath = fileStorageService.uploadFile(file, "notice");
        //     } catch (IOException e) {
        //         System.out.println("문제가 발생했습니다 : " + e);
        //     }
        // }

        Notice notice = Notice.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .image_path(requestDto.getFilePath())
                .build();
        Notice saveNotice = noticeRepository.save(notice);

        return NoticeResponseDto.builder()
                .id(saveNotice.getId())
                .title(saveNotice.getTitle())
                .content(saveNotice.getContent())
                .image_path(saveNotice.getImage_path())
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

        // String filePath;
        // try {
        //     filePath = fileStorageService.uploadFile(file,"notice");
        // } catch (IOException e) {
        //     filePath = "null";
        // }

        post.update(
                requestDto.getTitle(),
                requestDto.getContent(),
                requestDto.getFilePath());
        noticeRepository.save(post);

        return NoticeResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .image_path(post.getImage_path())
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
