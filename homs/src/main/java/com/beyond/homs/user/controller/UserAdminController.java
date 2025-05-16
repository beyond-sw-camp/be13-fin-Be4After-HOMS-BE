package com.beyond.homs.user.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.user.dto.CreateUserDto;
import com.beyond.homs.user.dto.UpdateUserDto;
import com.beyond.homs.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "User API(관리자)", description = "User API 목록")
public interface UserAdminController {
    @Operation(summary = "계정 등록", description = "관리자가 계정을 생성 합니다.")
    ResponseEntity<ResponseDto<Void>> createUser(CreateUserDto createUserDto);

    @Operation(summary = "계정 수정", description = "관리자가 유저의 계정을 수정 합니다.")
    ResponseEntity<ResponseDto<Void>> updateUser(Long userId, UpdateUserDto updateUserDto);

    @Operation(summary = "계정 목록 조회", description = "관리자가 계정 목록을 조회 합니다.")
    ResponseEntity<ResponseDto<List<UserResponseDto>>> getUsers();

    @Operation(summary = "계정 상세 조회", description = "관리자가 특정 계정을 조회 합니다.")
    ResponseEntity<ResponseDto<UserResponseDto>> getUser(Long userId);

    @Operation(summary = "계정 삭제", description = "관리자가 계정을 삭제 합니다.")
    ResponseEntity<ResponseDto<Void>> deleteUser(Long userId);
}
