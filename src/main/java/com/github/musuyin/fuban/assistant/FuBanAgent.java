package com.github.musuyin.fuban.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        streamingChatModel = "qwenStreamingChatModel",
        chatMemoryProvider = "chatMemoryProviderXiaoZhi",
        tools = "appointmentTools",
        contentRetriever = "contentRetrieverXiaozhiPincone"
)
public interface FuBanAgent {
    @SystemMessage()
    Flux<String> chat(@MemoryId long memoryId, @UserMessage String message);
}
