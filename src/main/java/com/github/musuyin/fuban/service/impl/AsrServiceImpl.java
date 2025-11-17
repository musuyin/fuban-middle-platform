package com.github.musuyin.fuban.service.impl;

import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import com.alibaba.dashscope.utils.Constants;
import com.alibaba.dashscope.utils.JsonUtils;
import com.github.musuyin.fuban.service.AsrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
@Slf4j
public class AsrServiceImpl implements AsrService {
    @Override
    public String transcribeAudio(String filePath) throws NoApiKeyException, UploadFileException {
        Constants.baseHttpApiUrl = "https://dashscope.aliyuncs.com/api/v1";
        MultiModalConversation conv = new MultiModalConversation();
        String fileUri = "file:///" + new File(filePath).getAbsolutePath().replace("\\", "/");

        MultiModalMessage userMessage = MultiModalMessage.builder()
                .role(Role.USER.getValue())
                .content(Arrays.asList(Collections.singletonMap("audio", fileUri)))
                .build();

        MultiModalMessage sysMessage = MultiModalMessage.builder().role(Role.SYSTEM.getValue())
                // 此处用于配置定制化识别的Context
                .content(Arrays.asList(Collections.singletonMap("text", "")))
                .build();

        Map<String, Object> asrOptions = new HashMap<>();
        asrOptions.put("enable_itn", true);
        // asrOptions.put("language", "zh"); // 可选，若已知音频的语种，可通过该参数指定待识别语种，以提升识别准确率

        MultiModalConversationParam param = MultiModalConversationParam.builder()
                .apiKey(System.getenv("API_KEY_ALI_BAILIAN"))
                .model("qwen3-asr-flash")
                .message(userMessage)
                .message(sysMessage)
                .parameter("asr_options", asrOptions)
                .build();

        MultiModalConversationResult result = conv.call(param);
        // 解析JSON结果获取文本
        String jsonString = JsonUtils.toJson(result);
        try {
            // 根据实际返回格式解析结果
            Map<String, Object> resultMap = JsonUtils.fromJson(jsonString, Map.class);
            if (resultMap != null && resultMap.containsKey("output")) {
                Map<String, Object> output = (Map<String, Object>) resultMap.get("output");
                if (output != null && output.containsKey("choices")) {
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) output.get("choices");
                    if (choices != null && !choices.isEmpty()) {
                        Map<String, Object> choice = choices.get(0);
                        if (choice != null && choice.containsKey("message")) {
                            Map<String, Object> message = (Map<String, Object>) choice.get("message");
                            if (message != null && message.containsKey("content")) {
                                // content 是一个数组，需要遍历提取所有文本
                                List<Map<String, Object>> contentArray = (List<Map<String, Object>>) message.get("content");
                                if (contentArray != null && !contentArray.isEmpty()) {
                                    StringBuilder sb = new StringBuilder();
                                    for (Map<String, Object> contentItem : contentArray) {
                                        if (contentItem != null && contentItem.containsKey("text")) {
                                            sb.append(contentItem.get("text"));
                                        }
                                    }
                                    log.info("ASR结果: {}", sb.toString());
                                    return sb.toString();
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("解析ASR结果失败: " + e.getMessage());
        }
        return "";
    }
}
