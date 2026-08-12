package com.project.resuming.resume.api.request;

import com.project.resuming.resume.domain.Resume;


public record ResumeSummaryResDto(
        Long resumeId,
        String targetCompany
) {
    public static ResumeSummaryResDto of(Resume resume){
        return new ResumeSummaryResDto(resume.getId(), resume.getTargetCompany());

    }
}
