package com.project.resuming.resume.api.response;


import com.project.resuming.selfresume.domain.SelfResume;
import lombok.Builder;

@Builder
public record ResumeInfoResDto(

        Long resumeId,
        String strengthAnalysis,
        String improvementAreas,
        String personalizedCoachingInsight,
        String aiRecommendedResumeContent

) {

    public static ResumeInfoResDto from(SelfResume resume){
        return ResumeInfoResDto.builder()
                .strengthAnalysis(resume.getStrengthAnalysis())
                .improvementAreas(resume.getImprovementAreas())
                .personalizedCoachingInsight(resume.getPersonalizedCoachingInsight())
                .aiRecommendedResumeContent(resume.getAiRecommendedResumeContent())
                .build();
    }
}
