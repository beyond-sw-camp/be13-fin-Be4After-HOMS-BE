package com.beyond.homs.common.email.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.common.email.dto.EmailPostDto;
import com.beyond.homs.common.email.entity.EmailMessage;
import com.beyond.homs.common.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notify/email")
public class EmailControllerImpl implements EmailController {

    private final EmailService emailService;

    @PostMapping("/")
    @Override
    public ResponseEntity<ResponseDto<String>> sendMail(
            @RequestBody EmailPostDto emailPostDto) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(emailPostDto.getEmail())
                .subject(emailPostDto.getSubject())
                .content(emailPostDto.getContent())
                .build();
        emailService.sendMail(emailMessage,emailPostDto.getEmailType());

        return ResponseEntity.ok().build();
    }
}
