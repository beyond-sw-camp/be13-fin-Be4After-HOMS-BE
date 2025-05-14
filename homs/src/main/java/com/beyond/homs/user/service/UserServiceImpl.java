package com.beyond.homs.user.service;

import com.beyond.homs.company.entity.Company;
import com.beyond.homs.company.entity.Department;
import com.beyond.homs.company.repository.CompanyRepository;
import com.beyond.homs.company.repository.DepartmentRepository;
import com.beyond.homs.user.data.UserRole;
import com.beyond.homs.user.dto.CreateUserDto;
import com.beyond.homs.user.entity.PasswordHistory;
import com.beyond.homs.user.entity.User;
import com.beyond.homs.user.entity.UserLogin;
import com.beyond.homs.user.repository.PasswordHistoryRepository;
import com.beyond.homs.user.repository.UserLoginRepository;
import com.beyond.homs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
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
                    throw new RuntimeException("User already exists");
                });

        System.out.println("createUserDto = *********" + createUserDto.companyId());

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

        List<UserLogin> userLogins = userLoginRepository.findAll();

        UserLogin userLogin = UserLogin.builder()
                .user(savedUser)
                .passwordHash(passwordEncoder.encode(createUserDto.password()))
                .lockedOut(false)
                .build();
        userLoginRepository.save(userLogin);
    }
}
