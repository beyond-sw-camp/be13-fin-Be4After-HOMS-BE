package com.beyond.homs.common.email.service;

import com.beyond.homs.common.email.data.EmailTypeEnum;
import com.beyond.homs.common.email.entity.EmailMessage;

public interface EmailService {
    String sendMail(EmailMessage emailMessage, EmailTypeEnum type);
}
