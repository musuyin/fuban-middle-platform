package com.github.musuyin.fuban.service.impl;

import com.github.musuyin.fuban.bean.ApiResponse;
import com.github.musuyin.fuban.service.AsrService;
import com.github.musuyin.fuban.service.AudioProcessService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        Path tempFile = null;
        try {
            // 创建临时文件
            String originalFileName = audio.getOriginalFilename();
            String fileExtension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            
            tempFile = Files.createTempFile("audio_", fileExtension);
            audio.transferTo(tempFile.toFile());

            // 获取文件绝对路径
            String filePath = tempFile.toAbsolutePath().toString();

            // 调用ASR服务进行语音识别
            text = asrService.transcribeAudio(filePath);
        } catch (Exception e) {
            // 更好的错误处理和日志记录
            return ApiResponse.Fail("音频处理失败: " + e.getMessage());
        } finally {
            // 删除临时文件
            if (tempFile != null && Files.exists(tempFile)) {
                try {
                    Files.delete(tempFile);
                } catch (IOException e) {
                    // 记录删除失败的日志，但不影响主流程
                    System.err.println("删除临时文件失败: " + e.getMessage());
                }
            }
        }

        // 封装结果返回
        return ApiResponse.Success(Map.of("text", text));
    }
}
