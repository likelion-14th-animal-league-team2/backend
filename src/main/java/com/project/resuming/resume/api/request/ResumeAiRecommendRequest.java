package com.project.resuming.resume.api.request;

import org.springframework.web.multipart.MultipartFile;

public record ResumeAiRecommendRequest(
        String userText,
        MultipartFile userImage,
        String jobText,
        MultipartFile jobImage
) {}