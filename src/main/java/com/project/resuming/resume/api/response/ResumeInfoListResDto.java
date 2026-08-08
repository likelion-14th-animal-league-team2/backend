package com.project.resuming.resume.api.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ResumeInfoListResDto(

        List<ResumeInfoResDto> resumList

) {

    public static ResumeInfoListResDto from(List<ResumeInfoResDto> resumeList){
        return ResumeInfoListResDto.builder()
                .resumList(resumeList)
                .build();
    }
}
