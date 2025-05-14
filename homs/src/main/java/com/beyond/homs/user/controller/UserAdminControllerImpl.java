package com.beyond.homs.user.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.user.dto.CreateUserDto;
import com.beyond.homs.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin/user")
@RequiredArgsConstructor
public class UserAdminControllerImpl implements UserAdminController {
    private final UserService userService;

    @Override
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto<>(
                        200,
                        "User Created Successfully",
                        null
                )
        );
    }
}
