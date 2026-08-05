package com.project.resuming.resume.api.response;

import com.project.resuming.selfresume.api.response.SelfResumeInfoResDto;
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
