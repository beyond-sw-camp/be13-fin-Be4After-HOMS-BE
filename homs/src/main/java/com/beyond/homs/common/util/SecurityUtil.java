package com.beyond.homs.common.util;

import com.beyond.homs.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    private SecurityUtil() {
        // 인스턴스화 방지
        // - 객체로 만들어질 필요가 없음을 명시
        // - 불필요한 자원 소모 막기 위함
    }

    // 현재 로그인한 유저의 정보를 반환
    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 현재 로그인된 정보가 없다면 null
        if (authentication == null) {
            return null;
        }

        // 로그인한 유저의 정보가 있다면 반환
        if (authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }

    // 현재 로그인한 유저 id 반환
    public static Long getCurrentUserId() {
        User currentUser = getCurrentUser();

        if (currentUser == null) {
            return null;
        }
        return currentUser.getUserId();
    }
}
