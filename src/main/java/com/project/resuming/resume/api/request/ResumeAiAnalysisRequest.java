package com.project.resuming.resume.api.request;

import org.springframework.web.multipart.MultipartFile;

public record ResumeAiAnalysisRequest(
        String resumeText,
        MultipartFile resumeImage,
        String jobText,
        MultipartFile jobImage,
        String targetCountry
) {}