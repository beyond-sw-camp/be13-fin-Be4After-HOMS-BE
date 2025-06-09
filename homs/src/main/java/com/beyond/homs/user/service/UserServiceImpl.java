package com.beyond.homs.user.service;

import com.beyond.homs.user.dto.UpdateUserDto;
import com.beyond.homs.user.dto.UserResponseDto;
import com.beyond.homs.user.entity.User;
import com.beyond.homs.user.entity.UserLogin;
import com.beyond.homs.user.repository.PasswordHistoryRepository;
import com.beyond.homs.user.repository.UserLoginRepository;
import com.beyond.homs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserLoginRepository userLoginRepository;
    private final PasswordHistoryRepository passwordHistoryRepository;

    @Override
    public UserResponseDto getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        return UserResponseDto.fromUser(user);
    }

    @Override
    @Transactional
    public void updateUser(Long userId, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        user.setManagerName(updateUserDto.managerName());
        user.setManagerEmail(updateUserDto.managerEmail());
        user.setManagerPhone(updateUserDto.managerPhone());

        if (updateUserDto.newPassword() != null) {
            UserLogin userLogin = user.getUserLogin();
            userLogin.setPasswordHash(
                    passwordEncoder.encode(updateUserDto.newPassword())
            );
            userLoginRepository.save(userLogin);
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void resetPassword(String userName) {
        /**
         * email 전송 추가 해야 함
         */
        User user = userRepository.findByUserName(userName)
                .orElseThrow(()-> new RuntimeException("User not found"));
        UserLogin userLogin = user.getUserLogin();
        userLogin.setPasswordHash(
                passwordEncoder.encode(createNewPassword())
        );
        userLoginRepository.save(userLogin);
    }

    private String createNewPassword() {
        StringBuilder passwordBuilder = new StringBuilder();
        char lowercaseA = 'a';
        for (int i = 0; i < 10; i++) {
            int random = (int) (Math.random() * 100) / 26;
            passwordBuilder.append(lowercaseA + random);
        }
        return passwordBuilder.toString();
    }
}
