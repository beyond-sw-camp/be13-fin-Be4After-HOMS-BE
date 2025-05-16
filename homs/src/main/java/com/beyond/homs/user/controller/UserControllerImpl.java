package com.beyond.homs.user.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.user.dto.ResetPasswordDto;
import com.beyond.homs.user.dto.UpdateUserDto;
import com.beyond.homs.user.dto.UserResponseDto;
import com.beyond.homs.user.service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    @Override
    public ResponseEntity<ResponseDto<UserResponseDto>> getUser(@PathVariable Long userId) {
        UserResponseDto userResponseDto = userService.getUser(userId);
        return ResponseEntity.ok(
                new ResponseDto<>(
                        200,
                        "User Retrieved Successfully",
                        userResponseDto
                )
        );
    }

    @PutMapping("/{userId}")
    @Override
    public ResponseEntity<ResponseDto<Void>> updateUser(@PathVariable Long userId,
                                                        @RequestBody @Valid UpdateUserDto updateUserDto) {
        userService.updateUser(userId, updateUserDto);
        return ResponseEntity.ok(
                new ResponseDto<>(
                        200,
                        "User Updated Successfully",
                        null
                )
        );
    }

    @PostMapping("/reset/password")
    @Override
    public ResponseEntity<ResponseDto<Void>> resetPassword(@RequestBody @Valid ResetPasswordDto resetPasswordDto) {
        return ResponseEntity.ok(
                new ResponseDto<>(
                        200,
                        "Password Reset Successfully",
                        null
                )
        );
    }
}
