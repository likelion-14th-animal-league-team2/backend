package com.project.resuming.resume.api.request;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record ResumeToBackReqDto(
        String resumeText,
        MultipartFile resumeImage,
        String jobText,
        MultipartFile jobImage,
        String targetCountry,
        String targetCompany
) {}