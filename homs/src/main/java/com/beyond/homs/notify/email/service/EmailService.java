package com.beyond.homs.notify.email.service;

import com.beyond.homs.notify.email.entity.EmailMessage;

import java.util.Map;

public interface EmailService {
    String sendMail(EmailMessage emailMessage, String type);
}
