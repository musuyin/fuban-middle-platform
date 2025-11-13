
package com.github.musuyin.fuban.bean;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

// 音频上传请求实体类
@Data
public class AudioTranscriptionRequest {
    private MultipartFile file;
}
