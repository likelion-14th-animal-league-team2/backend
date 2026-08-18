package com.project.resuming.security.oauth.kakao.api;

import com.project.resuming.common.response.ApiResTemplate;
import com.project.resuming.common.response.SuccessCode;
import com.project.resuming.security.dto.response.LoginResponseDto;
import com.project.resuming.security.oauth.kakao.application.KakaoOAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/kakao")
@Tag(name = "카카오 로그인 API", description = "카카오 소셜 로그인 API")
public class KakaoOAuthController {

    private final KakaoOAuthService kakaoOAuthService;

    @GetMapping("/callback")
    @Operation(summary = "카카오 로그인 콜백", description = "카카오 인가 코드를 받아 토큰을 발급하고 프론트로 리다이렉트합니다.")
    public ResponseEntity<Void> kakaoCallback(@RequestParam("code") String code) {
        LoginResponseDto response = kakaoOAuthService.kakaoLogin(code);

        String frontendBaseUrl = "https://front-blush-tau.vercel.app";
        String redirectPath = response.isRegistered() ? "/home" : "/onboarding";

        String targetUrl = UriComponentsBuilder
                .fromUriString(frontendBaseUrl + redirectPath)
                .queryParam("accessToken", response.accessToken())
                .queryParam("isRegistered", response.isRegistered())
                .build()
                .toUriString();

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(targetUrl))
                .build();
    }
}