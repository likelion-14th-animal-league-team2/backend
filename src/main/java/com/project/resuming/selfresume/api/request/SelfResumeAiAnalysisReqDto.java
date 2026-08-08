package com.project.resuming.selfresume.api.request;

import org.springframework.web.multipart.MultipartFile;

public record SelfResumeAiAnalysisReqDto(
        String resumeText,
        MultipartFile resumeImage,
        String jobText,
        String targetCompany,
        MultipartFile jobImage,
        String targetCountry
) {}