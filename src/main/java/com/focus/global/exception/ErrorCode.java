package com.focus.global.exception;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_REQUEST(400, "Bad Request", "INVALID_INPUT", "입력값이 올바르지 않습니다."),
    SERVER_ERROR(500, "Internal Server Error", "Internal Server Error", "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    USER_NOT_FOUND(404, "Not Found", "USER_NOT_FOUND", "사용자 정보를 찾을 수 없습니다."),
    APPLICATION_NOT_FOUND(404, "Not Found", "APPLICATION_NOT_FOUND", "해당 지원서 내역을 찾을 수 없습니다.");

    private final int status;
    private final String error;
    private final String code;
    private final String message;
}
