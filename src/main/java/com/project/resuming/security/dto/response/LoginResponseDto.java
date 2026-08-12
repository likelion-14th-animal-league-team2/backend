package com.project.resuming.security.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponseDto(
        @Schema(description = "발급된 Access Token")
        String accessToken,

        @Schema(description = "기존 회원가입 완료 여부 (false일 경우 추가 정보 입력 페이지로 이동)")
        boolean isRegistered
) {
}