package com.beyond.homs.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeResponseDto {
    private long id;

    private String title;

    private String content;

    private String imagePath;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
