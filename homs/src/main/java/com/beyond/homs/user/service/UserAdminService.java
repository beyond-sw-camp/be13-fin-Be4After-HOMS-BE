package com.beyond.homs.user.service;

import com.beyond.homs.user.dto.CreateUserDto;
import com.beyond.homs.user.dto.UpdateUserDto;
import com.beyond.homs.user.dto.UserResponseDto;

import java.util.List;

public interface UserAdminService {
    void createUser(CreateUserDto createUserDto);

    void updateUser(Long userId, UpdateUserDto updateUserDto);

    List<UserResponseDto> getUsers();

    UserResponseDto getUser(Long userId);

    void deleteUser(Long userId);
}
