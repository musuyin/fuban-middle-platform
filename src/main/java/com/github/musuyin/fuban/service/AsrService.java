package com.github.musuyin.fuban.service;

import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;

public interface AsrService {
    String transcribeAudio(byte[] audioBytes, String fileType) throws NoApiKeyException, UploadFileException;
}