package com.beyond.homs.notice.dto;

import com.beyond.homs.notice.entity.Notice;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeListDto {
    private final Long id;

    private final String title;

    private final LocalDateTime createTime;

    private final LocalDateTime updateTime;

    // JPQL에서 사용될 생성자 추가
    public NoticeListDto(Long id, String title, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.title = title;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public NoticeListDto(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.createTime = notice.getCreatedAt();
        this.updateTime = notice.getUpdatedAt();
    }
}
