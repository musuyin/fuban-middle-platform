
package com.github.musuyin.fuban.bean;

import lombok.Data;

// 统一API相应格式
@Data
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
}
