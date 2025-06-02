package com.beyond.homs.notify.email.service;

import com.beyond.homs.notify.email.data.EmailTypeEnum;
import com.beyond.homs.notify.email.entity.EmailMessage;

public interface EmailService {
    String sendMail(EmailMessage emailMessage, EmailTypeEnum type);
}
