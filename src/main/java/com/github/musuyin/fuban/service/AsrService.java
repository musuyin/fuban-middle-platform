package com.github.musuyin.fuban.service;

public interface AsrService {
    String transcribeAudio(byte[] audioBytes, String fileType);
}