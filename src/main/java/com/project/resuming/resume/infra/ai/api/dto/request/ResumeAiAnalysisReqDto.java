package com.project.resuming.resume.infra.ai.api.dto.request;

import lombok.Builder;

@Builder
public record ResumeAiAnalysisReqDto(
		Resume resume,
		String country,
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
			String imageText
	) {}
}
