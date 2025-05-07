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

    public NoticeListDto(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.createTime = notice.getCreatedAt();
        this.updateTime = notice.getUpdatedAt();
    }
}
