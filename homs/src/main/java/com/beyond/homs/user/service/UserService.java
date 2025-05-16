package com.beyond.homs.user.service;

import com.beyond.homs.user.dto.ResetPasswordDto;
import com.beyond.homs.user.dto.UpdateUserDto;
import com.beyond.homs.user.dto.UserResponseDto;

public interface UserService {
    UserResponseDto getUser(Long userId);

    void updateUser(Long userId, UpdateUserDto updateUserDto);

    void resetPassword(String userName);
}
