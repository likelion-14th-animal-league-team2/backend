package com.project.resuming.selfresume.api.response;

import com.project.resuming.selfresume.domain.SelfResume;

public record SelfResumeSummaryResDto(
        Long resumeId,
        String targetCompany
) {
    public static SelfResumeSummaryResDto of(SelfResume selfResume){
        return new SelfResumeSummaryResDto(selfResume.getId(), selfResume.getTargetCompany());

    }
}
