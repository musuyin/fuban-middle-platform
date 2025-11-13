package com.github.musuyin.fuban.bean;

import lombok.Data;

// 结构化的聊天信息
@Data
public class ChatMessage {
    private String speaker;
    private String message;
}
