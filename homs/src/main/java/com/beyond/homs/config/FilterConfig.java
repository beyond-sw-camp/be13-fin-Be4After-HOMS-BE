package com.beyond.homs.config;

import com.beyond.homs.auth.jwt.JwtAuthFilter;
import com.beyond.homs.auth.jwt.JwtAuthProvider;
import com.beyond.homs.common.filter.LogFilter;
import com.beyond.homs.common.jwt.JwtService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<LogFilter> logFilter() {
        FilterRegistrationBean<LogFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LogFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // 필터 순서 설정 (숫자가 낮을수록 먼저 실행됨)

        return registrationBean;
    }
}
