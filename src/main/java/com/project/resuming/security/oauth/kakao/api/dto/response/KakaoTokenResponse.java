package com.project.resuming.security.oauth.kakao.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoTokenResponse(

        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("token_type")
        String tokenType,

        @JsonProperty("expires_in")
        Integer expiresIn
) {
}