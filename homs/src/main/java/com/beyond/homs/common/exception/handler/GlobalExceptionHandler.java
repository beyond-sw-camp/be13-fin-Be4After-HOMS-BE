package com.beyond.homs.common.exception.handler;

import com.beyond.homs.common.exception.dto.ErrorResponseDto;
import com.beyond.homs.common.exception.exceptions.BaseException;
import com.beyond.homs.common.message.MessageSender;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSender messageSender;

    @Value("${spring.profiles.active}")
    private String profile;

    //  ResponseEntityExceptionHandler에서 상속한 메서드로, Spring 내부에서 예외가 발생했을 때 호추된다.
    // buildErrorResponse를 통해서 예외를 처리하도록 작성
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatusCode statusCode,
                                                             WebRequest request){
        return buildErrorResponse(ex, ex.getMessage(), HttpStatus.valueOf(statusCode.value()), request);
    }

    // 커스텀 예외 DTO의 형태로 응답을 생성하기 위한 메서드
    private ResponseEntity<Object> buildErrorResponse(Exception exception,
                                                      String message,
                                                      HttpStatus httpStatus,
                                                      WebRequest request) {
        errorReport(message, request.getDescription(true));
        String rid = ThreadContext.get("requestId");
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus.value(), rid, message, LocalDateTime.now());
        if (exception instanceof BaseException) {
            errorResponseDto.setErrorCode(((BaseException) exception).getErrorCode());
        }
        return ResponseEntity.status(httpStatus).body(errorResponseDto);
    }

    // 사용자 정의 예외 처리
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException ex, WebRequest request) {
        log.error("BaseException: {}", ex.getMessage());
        errorReport(ex.getMessage(), request.getDescription(true));
        String rid = ThreadContext.get("requestId");
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getStatus().value(), rid, ex.getMessage(), LocalDateTime.now());
        errorResponseDto.setErrorCode(ex.getErrorCode());
        return ResponseEntity.status(ex.getStatus()).body(errorResponseDto);
    }

    // 모든 예외에 대한 Handler
    // 정의되어 있지 않은 Exception Type을 처리
    // 500 Uncaught Exception
    @ExceptionHandler(Exception.class)
    @Hidden
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(Exception exception, WebRequest request) {
        log.error("Internal error occurred", exception);
        return buildErrorResponse(exception, exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // 인증 관련 예외 처리 : Spring Security에서 발생하는 인증 관련 예외를 처리
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException exception, WebRequest request) {
        log.error("Authentication error: {}", exception.getMessage());
        return buildErrorResponse(exception, exception.getMessage(), HttpStatus.UNAUTHORIZED, request);
    }

    // 권한 관련 예외 처리 : Spring Security에서 발생하는 권한 관련 예외를 처리
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception, WebRequest request) {
        log.error("Access denied error: {}", exception.getMessage());
        return buildErrorResponse(exception, exception.getMessage(), HttpStatus.FORBIDDEN, request);
    }

    private void errorReport(String message, String requestUri) {
        if ("prod".equals(profile)) { // Only send error reports in production
            String rid = ThreadContext.get("requestId");
            messageSender.send(String.format("```Exception occurred\n \tRequestId=%s \n \tMessage=%s \n \t%s```", rid, message, requestUri));
        }
    }
}
