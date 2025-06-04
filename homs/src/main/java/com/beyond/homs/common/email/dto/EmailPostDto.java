package com.beyond.homs.common.email.dto;

import com.beyond.homs.common.email.data.EmailTypeEnum;
import lombok.Getter;

@Getter
public class EmailPostDto {
    private Long id;

    private String email;

    private String subject;

    private String content;

    private EmailTypeEnum emailType;
}
