package com.beyond.homs.notify.email.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.notify.email.dto.EmailPostDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Email API", description = "Email API 목록")
public interface EmailController {
    @Operation(summary = "Example Endpoint", description = "예시")
    ResponseEntity<ResponseDto<String>> example(
            @RequestBody EmailPostDto emailPostDto);
}