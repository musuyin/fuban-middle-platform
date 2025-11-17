package com.github.musuyin.fuban.service.impl;

import com.github.musuyin.fuban.bean.ApiResponse;
import com.github.musuyin.fuban.service.ImageProcessService;
import com.github.musuyin.fuban.service.OcrService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Service
public class ImageProcessServiceImpl implements ImageProcessService {

    @Resource
    private OcrService ocrService;


    @Override
    public ApiResponse<Map<String, Object>> processImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return ApiResponse.Fail("上传的图片不能为空");
        }

        String text;
        Path tempFile = null;
        try {
            // 创建临时文件
            String originalFileName = image.getOriginalFilename();
            String fileExtension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }

            tempFile = Files.createTempFile("image_", fileExtension);
            image.transferTo(tempFile.toFile());

            // 获取文件绝对路径
            String filePath = tempFile.toAbsolutePath().toString();

            // 调用OCR服务进行图片识别
            text = ocrService.extractTextFromImage(filePath);
        } catch (Exception e) {
            // 更好的错误处理和日志记录
            return ApiResponse.Fail("OCR处理失败: " + e.getMessage());
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
