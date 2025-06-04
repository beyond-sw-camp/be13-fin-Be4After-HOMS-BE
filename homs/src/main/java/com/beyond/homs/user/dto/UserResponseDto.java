package com.beyond.homs.user.dto;

import com.beyond.homs.user.entity.User;

import java.time.LocalDateTime;

public record UserResponseDto(
    Long userId,
    String userName,
    String managerName,
    String managerEmail,
    String managerPhone,
    Long companyId,
    String companyName,
    Long deptId,
    String deptName,
    LocalDateTime deleteAt,
    boolean isLockedOut
) {
    public static UserResponseDto fromUser(User user) {
        boolean isLockedOut = false;
        if (user.getUserLogin() != null) {
            isLockedOut = user.getUserLogin().isLockedOut();
        }

        return new UserResponseDto(
                user.getUserId(),
                user.getUsername(),
                user.getManagerName(),
                user.getManagerEmail(),
                user.getManagerPhone(),
                user.getCompany().getCompanyId(),
                user.getCompany().getCompanyName(),
                user.getDepartment().getDeptId(),
                user.getDepartment().getDeptName().toString(),
                user.getDeletedAt(),
                isLockedOut
        );
    }
}
