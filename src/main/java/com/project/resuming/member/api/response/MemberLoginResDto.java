package com.project.resuming.member.api.response;

import lombok.Builder;

@Builder
public record MemberLoginResDto(

        String accessToken,
        boolean isRegistered // 회원가입 완료 여부 (추가 정보 입력 필요 여부)

) {

    public static MemberLoginResDto of(String accessToken, boolean isRegistered) {
        return MemberLoginResDto.builder()
                .accessToken(accessToken)
                .isRegistered(isRegistered)
                .build();
    }

    // 기존의 from 메서드와의 호환성이 필요하다면 추가 (기본값 true 처리)
    public static MemberLoginResDto from(String accessToken) {
        return MemberLoginResDto.builder()
                .accessToken(accessToken)
                .isRegistered(true)
                .build();
    }
}