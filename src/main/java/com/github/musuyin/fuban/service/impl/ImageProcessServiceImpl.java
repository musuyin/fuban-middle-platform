package com.github.musuyin.fuban.service.impl;

import com.github.musuyin.fuban.bean.ApiResponse;
import com.github.musuyin.fuban.service.ImageProcessService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class ImageProcessServiceImpl implements ImageProcessService {


    @Override
    public ApiResponse<Map<String, Object>> processImage(MultipartFile image) {
        return null;
    }
}
