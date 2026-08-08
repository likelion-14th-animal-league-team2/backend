package com.project.resuming.resume.infra.ai.api.dto.response;

import com.project.resuming.resume.domain.Resume;
import lombok.Builder;

@Builder
public record ResumeAiAnalysisResDto(
        String targetCompany,
        String strengthAnalysis,
        String improvementAreas,
        String personalizedCoachingInsight,
        String aiRecommendedResumeContent
) {


}