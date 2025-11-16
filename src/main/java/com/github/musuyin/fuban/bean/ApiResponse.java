
package com.github.musuyin.fuban.bean;

import lombok.Data;

import java.util.Map;

// 统一API相应格式
@Data
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> Success(T data) {
        return new ApiResponse<>(true, "success", data);
    }

    public static ApiResponse<Map<String, Object>> Fail(String message) {
        return new ApiResponse<>(false, message, null);
    }
}
