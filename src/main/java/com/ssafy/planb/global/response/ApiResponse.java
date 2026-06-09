package com.ssafy.planb.global.response;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private final String message;
    private final T data;

    private ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    // 메시지를 직접 지정하는 성공 응답
    // 데이터가 있는 성공 응답 (200 OK 등)
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data);
    }

    // 기본 메시지를 사용하는 성공 응답 (오버로딩)
    // 데이터가 있는 성공 응답 (200 OK 등)
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("요청이 성공적으로 처리되었습니다.", data);
    }

    // 반환할 데이터가 없는 성공 응답 (204 No Content 등에서 활용 가능)
    public static ApiResponse<Void> success() {
        return new ApiResponse<>("요청이 성공적으로 처리되었습니다.", null);
    }


}
