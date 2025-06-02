package com.beyond.homs.user.service;

import com.beyond.homs.company.entity.Company;
import com.beyond.homs.company.entity.Department;
import com.beyond.homs.company.repository.CompanyRepository;
import com.beyond.homs.company.repository.DepartmentRepository;
import com.beyond.homs.user.data.UserRole;
import com.beyond.homs.user.dto.CreateUserDto;
import com.beyond.homs.user.dto.UpdateUserDto;
import com.beyond.homs.user.dto.UserResponseDto;
import com.beyond.homs.user.entity.User;
import com.beyond.homs.user.entity.UserLogin;
import com.beyond.homs.user.repository.UserLoginRepository;
import com.beyond.homs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAdminServiceImpl implements UserAdminService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;
    private final UserLoginRepository userLoginRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUser(CreateUserDto createUserDto) {
        userRepository.findByUserName(createUserDto.userName())
                .ifPresent(user -> {
                    throw new RuntimeException("User name already exists");
                });

        Company company = companyRepository.findById(createUserDto.companyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Department department = departmentRepository.findById(createUserDto.deptId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        User user = User.builder()
                .userName(createUserDto.userName())
                .managerName(createUserDto.managerName())
                .managerEmail(createUserDto.managerEmail())
                .managerPhone(createUserDto.managerPhone())
                .role(UserRole.ROLE_USER)
                .company(company)
                .department(department)
                .build();
        User savedUser = userRepository.save(user);

        UserLogin userLogin = UserLogin.builder()
                .user(savedUser)
                .passwordHash(passwordEncoder.encode(createUserDto.password()))
                .lockedOut(false)
                .build();
        userLoginRepository.save(userLogin);
    }

    @Override
    public void updateUser(Long userId, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        user.setManagerName(updateUserDto.managerName());
        user.setManagerEmail(updateUserDto.managerEmail());
        user.setManagerPhone(updateUserDto.managerPhone());
        userRepository.save(user);
    }

    @Override
    public List<UserResponseDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::fromUser)
                .toList();
    }

    @Override
    public UserResponseDto getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        return UserResponseDto.fromUser(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.delete(); // 논리 삭제 처리

        UserLogin userLogin = userLoginRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("User login not found"));
        userLogin.lockAccount(); // 로그인 잠금 처리

        // 영속성 전이로 인해 user만 save해도 userLogin까지 반영될 수 있음
        userRepository.save(user);
    }
}
