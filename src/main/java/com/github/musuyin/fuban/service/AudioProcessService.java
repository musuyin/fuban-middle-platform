package com.github.musuyin.fuban.service;

import com.github.musuyin.fuban.bean.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface AudioProcessService {
    ApiResponse<Map<String, Object>> processAudio(MultipartFile audio);
}
