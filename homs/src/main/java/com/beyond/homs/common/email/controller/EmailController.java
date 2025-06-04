package com.beyond.homs.common.email.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.common.email.dto.EmailPostDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Email API", description = "Email API 목록")
public interface EmailController {
    @Operation(summary = "이메일 전송", description = "사용자에게 이메일을 전송합니다")
    ResponseEntity<ResponseDto<String>> sendMail(
            @RequestBody EmailPostDto emailPostDto);
}