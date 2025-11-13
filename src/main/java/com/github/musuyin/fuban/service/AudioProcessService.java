package com.github.musuyin.fuban.service;

import com.github.musuyin.fuban.bean.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface AudioProcessService {
    ResponseEntity<ApiResponse<Map<String, Object>>> processAudio(MultipartFile audio);
}
