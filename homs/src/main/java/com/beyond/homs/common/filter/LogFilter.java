package com.beyond.homs.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class LogFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", requestId);
        long start = System.currentTimeMillis();
        log.info("요청 시작 | RequestId={} | method={} | uri={}", requestId ,request.getMethod(), request.getRequestURI());
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        try {
            filterChain.doFilter(request, wrappedResponse);
        } finally {
            int status = wrappedResponse.getStatus();
            log.info("요청 종료 | RequestId={} | status={} | elapsed_time(ms)={}", requestId , status, System.currentTimeMillis() - start);
            wrappedResponse.copyBodyToResponse();
            ThreadContext.clearAll();
        }
    }
}
