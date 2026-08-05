package com.project.resuming.resume.infra.ai.api.dto.request;

import lombok.Builder;

@Builder
public record ResumeAiAnalysisReqDto(
		Resume resume,
		String currentCountry,    // 현재 거주 국가
		JobPosting jobPosting
) {
	@Builder
	public record Resume(
			String text,
			String imageText
	) {}

	@Builder
	public record JobPosting(
			String text,
			String imageText,
			String targetCountry
	) {}
}
