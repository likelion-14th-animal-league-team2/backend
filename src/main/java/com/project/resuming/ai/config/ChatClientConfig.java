package com.project.resuming.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    //이미지에서 텍스트 추출 chatClinet, gpt-4.1-mini사용
    @Bean
    public ChatClient imageToTextChatClient(ChatClient.Builder builder) {
        return builder
                .defaultOptions(
                        OpenAiChatOptions.builder()
                                .model("gpt-4.1-mini")

                )
                .defaultSystem("""
                    이 이미지에 포함된 모든 텍스트를 빠짐없이 추출해줘.
                    이력서, 포트폴리오, 채용 공고 스크린샷일 수 있어.
                    불필요한 설명 없이 텍스트 내용만 그대로 반환해.
                    """)
                .build();
    }


}
