package com.beyond.homs.auth.service;

import com.beyond.homs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String sub) throws UsernameNotFoundException {
        return userRepository.findById(Long.valueOf(sub))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + sub));
    }
}
