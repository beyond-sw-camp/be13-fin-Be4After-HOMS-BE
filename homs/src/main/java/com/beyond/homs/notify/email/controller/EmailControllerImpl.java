package com.beyond.homs.notify.email.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.notify.email.dto.EmailPostDto;
import com.beyond.homs.notify.email.entity.EmailMessage;
import com.beyond.homs.notify.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notify/send-mail")
public class EmailControllerImpl implements EmailController {

    private final EmailService emailService;

    @PostMapping("/example")
    @Override
    public ResponseEntity<ResponseDto<String>> example(
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
