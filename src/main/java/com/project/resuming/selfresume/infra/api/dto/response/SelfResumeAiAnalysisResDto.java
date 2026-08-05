package com.project.resuming.selfresume.infra.api.dto.response;

import lombok.Builder;

@Builder
public record SelfResumeAiAnalysisResDto(
        String strengthAnalysis,
        String improvementAreas,
        String personalizedCoachingInsight,
        String aiRecommendedResumeContent
) {


}