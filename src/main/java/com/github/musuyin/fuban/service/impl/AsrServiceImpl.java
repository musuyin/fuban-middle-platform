package com.github.musuyin.fuban.service.impl;

import com.github.musuyin.fuban.service.AsrService;
import org.springframework.stereotype.Service;

@Service
public class AsrServiceImpl implements AsrService {
    @Override
    public String transcribeAudio(byte[] audioBytes, String fileType) {
        return "";
    }
}
