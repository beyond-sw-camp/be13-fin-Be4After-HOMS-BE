package com.beyond.homs.user.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.user.dto.CreateUserDto;
import org.springframework.http.ResponseEntity;

public interface UserAdminController {
    ResponseEntity<ResponseDto<Void>> createUser(CreateUserDto createUserDto);
}
