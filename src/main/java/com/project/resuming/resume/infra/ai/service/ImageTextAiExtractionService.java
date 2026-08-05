package com.project.resuming.resume.infra.ai.service;

import com.project.resuming.common.exception.BusinessException;
import com.project.resuming.common.response.ErrorCode;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImageTextAiExtractionService {

    private final ChatClient chatClient;

    public ImageTextAiExtractionService(@Qualifier("imageToTextChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    //llm호출해서 이미지에서 텍스트 추출
    public String extractImageText(MultipartFile image){
        if (image == null || image.isEmpty()){
            return "";
        }

        Media media = toMedia(image);

        UserMessage userMessage = UserMessage.builder()
                .text("이 이미지에서 텍스트를 추출해줘") //default message설정했지만 유저 요청에서 텍스트 짧게라도 넣는게 안정적이라고 함.
                .media(media)
                .build();

        Prompt  prompt = new Prompt(userMessage);

        return chatClient.prompt(prompt)
                .call()
                .content();

    }

     /*
    springAi media타입 변경
    media타입에는 MIME 타입, 실제 바이너리 데이터 필요
     */
     private Media toMedia(MultipartFile file) {
         try {
             MimeType mimeType = MimeTypeUtils.parseMimeType(file.getContentType());
             return new Media(mimeType, file.getResource());
         } catch (Exception e) {
             throw new BusinessException(
                     ErrorCode.IMAGE_CONVERSION_TO_TEXT_FAILED,
                     ErrorCode.IMAGE_CONVERSION_TO_TEXT_FAILED.getMessage()
             );
         }
     }

}
