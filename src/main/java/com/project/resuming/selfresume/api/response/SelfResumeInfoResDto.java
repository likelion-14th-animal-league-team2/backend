package com.project.resuming.selfresume.api.response;


import com.project.resuming.selfresume.domain.SelfResume;
import lombok.Builder;

@Builder
public record SelfResumeInfoResDto(

        Long resumeId,
        String strengthAnalysis,
        String improvementAreas,
        String personalizedCoachingInsight,
        String aiRecommendedResumeContent

) {

    public static SelfResumeInfoResDto from(SelfResume resume){
        return SelfResumeInfoResDto.builder()
                .strengthAnalysis(resume.getStrengthAnalysis())
                .improvementAreas(resume.getImprovementAreas())
                .personalizedCoachingInsight(resume.getPersonalizedCoachingInsight())
                .aiRecommendedResumeContent(resume.getAiRecommendedResumeContent())
                .build();
    }
}
