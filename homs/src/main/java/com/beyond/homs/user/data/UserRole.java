package com.beyond.homs.user.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum UserRole {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_GUEST("ROLE_GUEST");

    @Getter
    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    @JsonCreator
    public static UserRole from(String role) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.getRole().equals(role)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + role);
    }

    @JsonValue
    public String getValue() {
        return role;
    }
}
