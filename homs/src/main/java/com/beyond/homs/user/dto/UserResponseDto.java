package com.beyond.homs.user.dto;

import com.beyond.homs.user.entity.User;

public record UserResponseDto(
    Long userId,
    String userName,
    String managerName,
    String managerEmail,
    String managerPhone,
//    Long companyId,
//    Long deptId
    String companyName,
    String deptName
) {
    public static UserResponseDto fromUser(User user) {
        return new UserResponseDto(
            user.getUserId(),
            user.getUsername(),
            user.getManagerName(),
            user.getManagerEmail(),
            user.getManagerPhone(),
            user.getCompany().getCompanyName(),
            user.getDepartment().getDeptName().toString()
        );
    }
}
