package com.project.resuming.selfresume.infra.api.dto.response;

import lombok.Builder;

@Builder
public record SelfResumeAiAnalysisResDto(
        String targetCompany,
        String strengthAnalysis,
        String improvementAreas,
        String personalizedCoachingInsight,
        String aiRecommendedResumeContent
) {


}