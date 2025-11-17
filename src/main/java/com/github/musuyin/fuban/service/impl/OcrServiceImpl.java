package com.github.musuyin.fuban.service.impl;

import com.github.musuyin.fuban.service.OcrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import com.alibaba.dashscope.utils.Constants;

@Service
@Slf4j
public class OcrServiceImpl implements OcrService {

    @Override
    public String extractTextFromImage(String imagePath) throws ApiException, NoApiKeyException, UploadFileException {
        MultiModalConversation conv = new MultiModalConversation();
        String fileUri = "file:///" + new File(imagePath).getAbsolutePath().replace("\\", "/");

        Map<String, Object> map = new HashMap<>();
        map.put("image", fileUri);
        // 输入图像的最大像素阈值，超过该值图像会按原比例缩小，直到总像素低于max_pixels
        map.put("max_pixels", "6422528");
        // 输入图像的最小像素阈值，小于该值图像会按原比例放大，直到总像素大于min_pixels
        map.put("min_pixels", "3136");
        // 开启图像自动转正功能
        map.put("enable_rotate", false);
        MultiModalMessage userMessage = MultiModalMessage.builder().role(Role.USER.getValue())
                .content(Arrays.asList(
                        map,
                        // qwen-vl-ocr-latest未设置内置任务时，支持在以下text字段中传入Prompt，若未传入则使用默认的Prompt：Please output only the text content from the image without any additional descriptions or formatting.
                        // 如调用qwen-vl-ocr-1028，模型会使用固定Prompt：Read all the text in the image.，不支持用户在text中传入自定义Prompt
                        Collections.singletonMap("text", "请提取微信聊天记录图像中的聊天记录，根据发送者不同使用换行进行划分，但是不需要写入发送者昵称，只保留内容即可"))).build();

        MultiModalConversationParam param = MultiModalConversationParam.builder()
                // 若没有配置环境变量，请用百炼API Key将下行替换为：.apiKey("sk-xxx")
                // 新加坡和北京地域的API Key不同。获取API Key：https://help.aliyun.com/zh/model-studio/get-api-key
                .apiKey(System.getenv("API_KEY_ALI_BAILIAN"))
                .model("qwen-vl-ocr-latest")
                .message(userMessage)
                .topP(0.001)
                .temperature(0.1f)
                .maxLength(8192)
                .build();

        MultiModalConversationResult result = conv.call(param);
        String ret = result.getOutput().getChoices().get(0).getMessage().getContent().get(0).get("text").toString();
        log.info("result: {}", ret);
        return ret;
    }
}
