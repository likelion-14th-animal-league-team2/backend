package com.project.resuming.kakao.api;

import com.project.resuming.common.response.ApiResTemplate;
import com.project.resuming.common.response.SuccessCode;
import com.project.resuming.security.dto.response.LoginResponseDto;
import com.project.resuming.kakao.application.KakaoOAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/kakao")
@Tag(name = "카카오 로그인 API", description = "카카오 소셜 로그인 API")
public class KakaoOAuthController {

    private final KakaoOAuthService kakaoOAuthService;

    @GetMapping("/callback")
    @Operation(summary = "카카오 로그인 콜백", description = "카카오 인가 코드를 받아 토큰을 발급하고 회원가입 여부를 반환합니다.")
    public ApiResTemplate<LoginResponseDto> kakaoCallback(@RequestParam("code") String code) {
        LoginResponseDto response = kakaoOAuthService.kakaoLogin(code);
        return ApiResTemplate.successResponse(SuccessCode.LOGIN_SUCCESS, response);
    }
}