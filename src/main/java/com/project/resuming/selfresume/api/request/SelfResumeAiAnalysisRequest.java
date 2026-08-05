package com.project.resuming.selfresume.api.request;

import org.springframework.web.multipart.MultipartFile;

public record SelfResumeAiAnalysisRequest(
        String resumeText,
        MultipartFile resumeImage,
        String jobText,
        MultipartFile jobImage,
        String targetCountry
) {}