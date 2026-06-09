package com.ssafy.planb.global.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Member (400)
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "M-001", "이미 존재하는 이메일입니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "M-002", "이메일이 존재 하지 않습니다."),

    // Auth (401, 403)
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A-001", "인증 자격 증명이 유효하지 않습니다."),

    // Global (500)
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G-001", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
