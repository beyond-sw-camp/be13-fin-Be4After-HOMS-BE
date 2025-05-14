package com.beyond.homs.user.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.user.dto.CreateUserDto;
import com.beyond.homs.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserAdminControllerImpl implements UserAdminController {
    private final UserService userService;

    @Override
    public ResponseEntity<ResponseDto<Void>> createUser(CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return ResponseEntity.ok(
                new ResponseDto<>(
                        200,
                        "User Created Successfully",
                        null
                )
        );
    }
}
