package com.beyond.homs.example.controller;

import com.beyond.homs.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Example API", description = "Example API 목록")
public interface ExampleController {
    @Operation(summary = "Example Endpoint", description = "예시")
    ResponseEntity<ResponseDto<String>> example();
}
/**
 * create database homs;
 * CREATE USER `beyond`@`%` IDENTIFIED BY 'beyond';
 * GRANT ALL PRIVILEGES ON homs.* TO `beyond`@`%`;
 * FLUSH PRIVILEGES;
 */