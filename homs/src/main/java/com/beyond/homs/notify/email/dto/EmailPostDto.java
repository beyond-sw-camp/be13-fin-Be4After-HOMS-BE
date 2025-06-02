package com.beyond.homs.notify.email.dto;

import com.beyond.homs.notify.email.data.EmailTypeEnum;
import lombok.Getter;

@Getter
public class EmailPostDto {
    private String email;

    private String subject;

    private String content;

    private EmailTypeEnum emailType;
}
