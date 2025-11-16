package com.github.musuyin.fuban.controller;

import com.github.musuyin.fuban.bean.ApiResponse;
import com.github.musuyin.fuban.service.AudioProcessService;
import com.github.musuyin.fuban.service.ImageProcessService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class FileProcessController {
    @Resource
    private ImageProcessService imageProcessService;

    @Resource
    private AudioProcessService audioProcessService;

    /**
     * 处理微信聊天截图
     */
    @PostMapping(value = "chat/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Map<String, Object>> processImage(
            @RequestParam("file") MultipartFile image) {
        log.info("接收到图片文件: {}", image.getOriginalFilename());
        return imageProcessService.processImage(image);
    }

    /**
     * 处理语音文件转文本
     */
    @PostMapping(value = "chat/audio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Map<String, Object>> processAudio(
            @RequestParam("file") MultipartFile audio) {
        log.info("接收到语音文件: {}", audio.getOriginalFilename());
        return audioProcessService.processAudio(audio);
    }
}
