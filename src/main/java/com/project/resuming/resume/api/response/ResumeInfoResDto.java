package com.project.resuming.resume.api.response;

import com.project.resuming.resume.domain.Resume;
import com.project.resuming.resume.infra.ai.api.dto.response.ResumeAiAnalysisResDto;
import lombok.Builder;

@Builder
public record ResumeInfoResDto(

        Long resumeId,
        String strengthAnalysis,
        String improvementAreas,
        String personalizedCoachingInsight,
        String aiRecommendedResumeContent

) {

    public static ResumeInfoResDto from(Resume resume){
        return ResumeInfoResDto.builder()
                .strengthAnalysis(resume.getStrengthAnalysis())
                .improvementAreas(resume.getImprovementAreas())
                .personalizedCoachingInsight(resume.getPersonalizedCoachingInsight())
                .aiRecommendedResumeContent(resume.getAiRecommendedResumeContent())
                .build();
    }
}
