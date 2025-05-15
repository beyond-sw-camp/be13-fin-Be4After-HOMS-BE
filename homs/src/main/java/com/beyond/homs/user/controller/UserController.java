package com.beyond.homs.user.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.user.dto.ResetPasswordDto;
import com.beyond.homs.user.dto.UpdateUserDto;
import com.beyond.homs.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "User API(사용자)", description = "User API 목록")
public interface UserController {
    @Operation(summary = "계정 상세 조회", description = "특정 계정을 조회 합니다.")
    ResponseEntity<ResponseDto<UserResponseDto>> getUser(Long userId);

    @Operation(summary = "계정 수정", description = "유저의 계정을 수정 합니다.")
    ResponseEntity<ResponseDto<Void>> updateUser(Long userId, UpdateUserDto updateUserDto);

    @Operation(summary = "패스워드 초기화", description = "패스워드 초기화")
    ResponseEntity<ResponseDto<Void>> resetPassword(ResetPasswordDto resetPasswordDto);
}
