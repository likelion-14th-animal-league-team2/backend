package com.project.resuming.resume.api.response;


import com.project.resuming.resume.domain.Resume;
import lombok.Builder;

@Builder
public record ResumeInfoResDto(

        Long resumeId,
        String targetCompany,
        String strengthAnalysis,
        String improvementAreas,
        String personalizedCoachingInsight,
        String aiRecommendedResumeContent

) {

    public static ResumeInfoResDto from(Resume resume){
        return ResumeInfoResDto.builder()
                .resumeId(resume.getId())
                .targetCompany(resume.getTargetCompany())
                .strengthAnalysis(resume.getStrengthAnalysis())
                .improvementAreas(resume.getImprovementAreas())
                .personalizedCoachingInsight(resume.getPersonalizedCoachingInsight())
                .aiRecommendedResumeContent(resume.getAiRecommendedResumeContent())
                .build();
    }
}
