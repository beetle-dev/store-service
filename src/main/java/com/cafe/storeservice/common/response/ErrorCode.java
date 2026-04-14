package com.cafe.storeservice.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND         ("S001", "해당 정보가 없습니다.",         HttpStatus.NOT_FOUND),
    VALIDATION_FAILED ("S006", "입력값이 유효하지 않습니다.",    HttpStatus.BAD_REQUEST),
    AUTH_INVALID_CREDENTIALS("A007", "이메일 또는 비밀번호가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED);

    private final String code;
    private final String message;
    private final HttpStatus status;  // ← 추가
}
