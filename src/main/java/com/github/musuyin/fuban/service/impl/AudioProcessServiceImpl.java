package com.github.musuyin.fuban.service.impl;

import com.github.musuyin.fuban.bean.ApiResponse;
import com.github.musuyin.fuban.service.AsrService;
import com.github.musuyin.fuban.service.AudioProcessService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class AudioProcessServiceImpl implements AudioProcessService {

    @Resource
    private AsrService asrService;
    @Override
    public ResponseEntity<ApiResponse<Map<String, Object>>> processAudio(MultipartFile audio) {
        return null;
    }
}
