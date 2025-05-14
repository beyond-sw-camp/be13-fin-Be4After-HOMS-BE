package com.beyond.homs.user.service;

import com.beyond.homs.user.dto.CreateUserDto;
import com.beyond.homs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void createUser(CreateUserDto createUserDto) {
        userRepository.findByUserName(createUserDto.userName())
                .ifPresent(user -> new RuntimeException("User already exists"));

        // 회사 조회 추가
        // 부서 조회 추가

    }
}
