package com.github.musuyin.fuban.service.impl;

import com.github.musuyin.fuban.bean.ApiResponse;
import com.github.musuyin.fuban.service.AsrService;
import com.github.musuyin.fuban.service.AudioProcessService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class AudioProcessServiceImpl implements AudioProcessService {

    @Resource
    private AsrService asrService;

    @Override
    public ApiResponse<Map<String, Object>> processAudio(MultipartFile audio) {
        // 检查文件是否为空
        if (audio == null || audio.isEmpty()) {
            return ApiResponse.Fail("上传的音频文件不能为空");
        }

        String text;
        try {
            byte[] audioBytes = audio.getBytes();
            String fileType = audio.getContentType();

            // 验证文件类型
            if (fileType == null || fileType.isEmpty()) {
                return ApiResponse.Fail("无法识别音频文件类型");
            }

            // 调用ASR服务进行语音识别
            text = asrService.transcribeAudio(audioBytes, fileType);
        } catch (Exception e) {
            // 更好的错误处理和日志记录
            return ApiResponse.Fail("音频处理失败: " + e.getMessage());
        }

        // 封装结果返回
        return ApiResponse.Success(Map.of("text", text));
    }
}
