package com.beyond.homs.notice.dto;

import com.beyond.homs.notice.entity.Notice;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeListDto {
    private final Long id;

    private final String title;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    // JPQL에서 사용될 생성자 추가
    public NoticeListDto(Long id, String title, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public NoticeListDto(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.createdAt = notice.getCreatedAt();
        this.updatedAt = notice.getUpdatedAt();
    }
}
