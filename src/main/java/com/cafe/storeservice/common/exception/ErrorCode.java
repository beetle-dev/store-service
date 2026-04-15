package com.cafe.storeservice.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND         ("S001", "해당 정보가 없습니다.", HttpStatus.NOT_FOUND),
    S3_UPLOAD_FAILED("S002", "파일 업로드에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    S3_DELETE_FAILED("S003", "파일 삭제에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    S3_FILE_NOT_FOUND("S004", "파일을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    S3_INVALID_FILE("S005", "유효하지 않은 파일입니다.", HttpStatus.BAD_REQUEST),
    VALIDATION_FAILED ("S006", "입력값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    S3_FILE_SIZE_EXCEEDED("S007","파일 크기가 초과되었습니다.", HttpStatus.PAYLOAD_TOO_LARGE);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
