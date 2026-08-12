package com.project.resuming.selfresume.api.response;

import lombok.Builder;

import java.util.List;

@Builder
public record SelfResumeInfoListResDto(

        List<SelfResumeInfoResDto> resumList

) {

    public static SelfResumeInfoListResDto from(List<SelfResumeInfoResDto> resumeList){
        return SelfResumeInfoListResDto.builder()
                .resumList(resumeList)
                .build();
    }
}
