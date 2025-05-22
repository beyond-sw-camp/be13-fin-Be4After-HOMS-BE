package com.beyond.homs.user.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.user.dto.CreateUserDto;
import com.beyond.homs.user.dto.UpdateUserDto;
import com.beyond.homs.user.dto.UserResponseDto;
import com.beyond.homs.user.service.UserAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/user")
@RequiredArgsConstructor
public class UserAdminControllerImpl implements UserAdminController {
    private final UserAdminService userService;

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

    @Override
    @PutMapping("/{userId}")
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

    @Override
    @GetMapping
    public ResponseEntity<ResponseDto<List<UserResponseDto>>> getUsers() {
        List<UserResponseDto> users = userService.getUsers();
        return ResponseEntity.ok(
                new ResponseDto<>(
                        200,
                        "User List Retrieved Successfully",
                        users
                )
        );
    }

    @Override
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto<UserResponseDto>> getUser(@PathVariable Long userId) {
        UserResponseDto user = userService.getUser(userId);
        return ResponseEntity.ok(
                new ResponseDto<>(
                        200,
                        "User Retrieved Successfully",
                        user
                )
        );
    }

    @Override
    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseDto<Void>> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(
                new ResponseDto<>(
                        200,
                        "User Deleted Successfully",
                        null
                )
        );
    }
}
