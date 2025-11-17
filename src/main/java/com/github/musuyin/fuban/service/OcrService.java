package com.github.musuyin.fuban.service;


import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;

public interface OcrService {
    String extractTextFromImage(String imagePath) throws NoApiKeyException, UploadFileException;
}
