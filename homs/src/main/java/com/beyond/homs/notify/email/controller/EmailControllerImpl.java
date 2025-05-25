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
                .subject("테스트 이메일입니다.")
                .build();
        emailService.sendMail(emailMessage,"email");

        return ResponseEntity.ok().build();
    }
}
