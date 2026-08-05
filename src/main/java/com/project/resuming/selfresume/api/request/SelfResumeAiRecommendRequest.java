package com.project.resuming.selfresume.api.request;

import org.springframework.web.multipart.MultipartFile;

public record SelfResumeAiRecommendRequest(
        String userText,
        MultipartFile userImage,
        String jobText,
        MultipartFile jobImage,
        String targetCountry
) {}