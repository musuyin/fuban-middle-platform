
package com.github.musuyin.fuban.bean;

import lombok.Data;

import java.util.Map;

// 统一API相应格式
@Data
public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T data;

    public ApiResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> Success(T data) {
        return new ApiResponse<>(200, "success", data);
    }

    public static ApiResponse<Map<String, Object>> Fail(String message) {
        return new ApiResponse<>(400, message, null);
    }
}
