package com.project.resuming.member.api.response;

import lombok.Builder;

@Builder
public record MemberLoginResDto(

        String accessToken

) {

    public static MemberLoginResDto from(String accessToken){
        return MemberLoginResDto.builder()
                .accessToken(accessToken)
                .build();
    }

}
