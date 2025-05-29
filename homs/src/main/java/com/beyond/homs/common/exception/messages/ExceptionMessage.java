package com.beyond.homs.common.exception.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {
    // 이곳에 사용자 정의 에러 메세지 입력

    // 공통 예시
    INTERNAL_SERVER_ERROR("G001", "서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INPUT_INVALID_VALUE("G002", "요청 값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    PAGE_OFFSET_MAX_VALUE("G003","페이지 오프셋이 최대 허용 범위를 초과했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_NULL_ERROR("G004","필수 입력 항목이 누락되었습니다.", HttpStatus.BAD_REQUEST),
    INVALID_FILE_FORMAT("G005", "지원하지 않는 파일 형식입니다.", HttpStatus.BAD_REQUEST),
    FILE_UPLOAD_FAILED("G006", "파일 업로드에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // 게시판 관련 에러
    POST_NOT_FOUND("P001","해당 게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    POST_TITLE_EMPTY("P002", "게시글 제목은 필수 항목입니다.", HttpStatus.BAD_REQUEST),
    POST_CONTENT_EMPTY("P003", "게시글 내용은 필수 항목입니다.", HttpStatus.BAD_REQUEST),
    POST_ACCESS_DENIED("P004", "해당 게시글에 대한 접근 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // 회원 관련 에러 (예시)
    USER_NOT_FOUND("U001", "해당 회원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_USERNAME("U002", "이미 사용 중인 아이디입니다.", HttpStatus.CONFLICT),
    PASSWORD_MISMATCH("U003", "비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    EMAIL_FORMAT_INVALID("U004", "이메일 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_PERMISSION_USER("U005","접근 권한이 없습니다!",HttpStatus.BAD_REQUEST),

    // 주문 관련 에러 (예시)
    ORDER_NOT_FOUND("O001", "해당 주문을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ORDER_ITEM_NOT_FOUND("O002", "해당 주문 상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INSUFFICIENT_STOCK("O003", "재고가 부족합니다.", HttpStatus.CONFLICT),

    // 상품 관리
    PRODUCT_NOT_FOUND("C001","해당 상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND("C001","해당 카테고리를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // 창고 관리
    WAREHOUSE_NOT_FOUND("W001","해당 창고를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // 파일 관리
    UNSUPPORTED_FILE_TYPE("F001", "지원되지 않는 파일 타입입니다.", HttpStatus.BAD_REQUEST),

    // 검색
    INVALID_SEARCH_KEYWORD("S001","검색어가 유효하지 않거나 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;

}
