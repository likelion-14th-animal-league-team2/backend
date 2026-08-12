package com.project.resuming.security.oauth.kakao.application;

import com.project.resuming.common.exception.BusinessException;
import com.project.resuming.common.response.ErrorCode;
import com.project.resuming.security.oauth.kakao.api.dto.response.KakaoTokenResponse;
import com.project.resuming.security.oauth.kakao.api.dto.response.KakaoUserInfoResponse;
import com.project.resuming.security.dto.response.LoginResponseDto;
import com.project.resuming.member.domain.Member;
import com.project.resuming.member.domain.repository.MemberRepository;
import com.project.resuming.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoOAuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final RestClient restClient = RestClient.create();

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client-secret}")
    private String clientSecret;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoUri;

    @Transactional
    public LoginResponseDto kakaoLogin(String code) {

        // 1. 카카오 토큰 요청
        KakaoTokenResponse tokenResponse = requestToken(code);

        // 2. 카카오 유저 정보 요청
        KakaoUserInfoResponse userInfoResponse = requestUserInfo(tokenResponse.accessToken());

        String email = userInfoResponse.getEmail();
        String nickname = userInfoResponse.getNickname();

        // 3. 회원 조회 또는 임시 신규 생성
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        Member member;
        boolean isRegistered;

        if (optionalMember.isPresent()) {
            member = optionalMember.get();
            // 나이(age)와 국가(country) 정보가 모두 들어있는지 확인
            isRegistered = isMemberProfileComplete(member);
        } else {
            // 신규 회원 등록 (기본 정보만 저장, 나이/국가는 null로 저장됨)
            member = memberRepository.save(
                    Member.builder()
                            .name(nickname)
                            .email(email)
                            .password(null) // 소셜 로그인은 비밀번호 없음
                            .build()
            );
            isRegistered = false; // 신규 회원이므로 추가 정보 필요
        }

        // 4. Access Token 발급 (30일 유효)
        String accessToken = jwtTokenProvider.generateAccessToken(member);

        return new LoginResponseDto(accessToken, isRegistered);
    }

    /**
     * 회원의 필수 추가 정보(나이, 국가) 입력 완료 여부 확인
     */
    private boolean isMemberProfileComplete(Member member) {
        return member.getAge() > 0 && StringUtils.hasText(member.getCountry());
    }

    private KakaoTokenResponse requestToken(String code) {
        try {
            return restClient.post()
                    .uri(tokenUri)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(
                            "grant_type=authorization_code"
                                    + "&client_id=" + clientId
                                    + "&client_secret=" + clientSecret
                                    + "&redirect_uri=" + redirectUri
                                    + "&code=" + code
                    )
                    .retrieve()
                    .body(KakaoTokenResponse.class);

        } catch (Exception e) {
            log.error("Kakao Token Request Failed: ", e);
            throw new BusinessException(
                    ErrorCode.KAKAO_LOGIN_FAILED_EXCEPTION,
                    "카카오 토큰 발급에 실패했습니다."
            );
        }
    }

    private KakaoUserInfoResponse requestUserInfo(String accessToken) {
        try {
            return restClient.get()
                    .uri(userInfoUri)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .body(KakaoUserInfoResponse.class);

        } catch (Exception e) {
            throw new BusinessException(
                    ErrorCode.KAKAO_LOGIN_FAILED_EXCEPTION,
                    "카카오 사용자 정보 조회에 실패했습니다."
            );
        }
    }
}