
package com.github.musuyin.fuban.bean;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

// 图片上传请求实体类
@Data
public class ChatImageRequest {
    private MultipartFile file;
}
