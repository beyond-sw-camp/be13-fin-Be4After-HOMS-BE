package com.beyond.homs;

import com.beyond.homs.company.data.CountryEnum;
import com.beyond.homs.company.entity.Company;
import com.beyond.homs.company.entity.Country;
import com.beyond.homs.company.repository.CompanyRepository;
import com.beyond.homs.company.repository.CountryRepository;
import com.beyond.homs.user.data.UserRole;
import com.beyond.homs.user.dto.CreateUserDto;
import com.beyond.homs.user.entity.User;
import com.beyond.homs.user.entity.UserLogin;
import com.beyond.homs.user.repository.UserLoginRepository;
import com.beyond.homs.user.repository.UserRepository;
import com.beyond.homs.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    public void createUserTest() {
        Country country = countryRepository.findByCountryName(CountryEnum.KOREA);

        Company company = Company.builder()
                .country(country)
                .companyName("Test Company")
                .registrationNumber("123456789")
                .representName("Adrian")
                .representPhone("010-1234-5678")
                .representCall("010-1234-5678")
                .representManagerName("Adrian")
                .representManagerEmail("a@a.com")
                .build();

        companyRepository.save(company);

        CreateUserDto createUserDto = new CreateUserDto(
                "testuser",
                "password123",
                "John Doe",
                "a@a.bc",
                "010-1234-5678",
                UserRole.ROLE_USER,
                company.getCompanyId(),
                1L);

        userService.createUser(createUserDto);

        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(1);
        User user = users.get(0);
        assertThat(user.getUserName()).isEqualTo("testuser");

        List<UserLogin> userLogins = userLoginRepository.findAll();
        assertThat(userLogins.size()).isEqualTo(1);
        UserLogin userLogin = userLogins.get(0);
        assertThat(userLogin.getUser().getUserName()).isEqualTo("testuser");
    }
}
