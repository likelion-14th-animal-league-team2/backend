package com.project.resuming.selfresume.infra.api.dto.request;

import lombok.Builder;

@Builder
public record SelfResumeAiAnalysisReqDto(
		SelfResume resume,
		String currentCountry,
		String targetCompany,
		JobPosting jobPosting
) {
	@Builder
	public record SelfResume(
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
