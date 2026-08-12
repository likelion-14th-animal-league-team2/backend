package com.project.resuming.member.application;

import com.project.resuming.common.exception.BusinessException;
import com.project.resuming.common.response.ErrorCode;
import com.project.resuming.member.api.request.MemberCompleteProfileReqDto;
import com.project.resuming.member.api.request.MemberLoginReqDto;
import com.project.resuming.member.api.request.MemberSignUpReqDto;
import com.project.resuming.member.api.request.MemberUpdateReqDto;
import com.project.resuming.member.api.response.MemberInfoResDto;
import com.project.resuming.member.api.response.MemberLoginResDto;
import com.project.resuming.member.domain.Member;
import com.project.resuming.member.domain.repository.MemberRepository;
import com.project.resuming.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 카카오 로그인 후 추가 프로필(나이, 국가) 입력 완료 처리
     */
    @Transactional
    public MemberInfoResDto completeProfile(Long memberId, MemberCompleteProfileReqDto reqDto) {
        Member member = findMember(memberId);

        // Member 엔티티의 나이/나라 정보 업데이트
        member.completeProfile(reqDto.age(), reqDto.country());

        return MemberInfoResDto.from(member);
    }

    /**
     * 로컬 회원가입
     */
    @Transactional
    public void localSignUp(MemberSignUpReqDto memberJoinReqDto) {
        if (memberRepository.existsByEmail(memberJoinReqDto.email())) {
            throw new BusinessException(
                    ErrorCode.ALREADY_EXIST_EMAIL,
                    ErrorCode.ALREADY_EXIST_EMAIL.getMessage());
        }

        Member member = Member.builder()
                .name(memberJoinReqDto.name())
                .age(memberJoinReqDto.age())
                .country(memberJoinReqDto.country())
                .email(memberJoinReqDto.email())
                .password(passwordEncoder.encode(memberJoinReqDto.password()))
                .build();

        memberRepository.save(member);
    }

    /**
     * 로컬 로그인
     */
    public MemberLoginResDto localLogin(MemberLoginReqDto memberLoginReqDto) {
        Member member = memberRepository.findByEmail(memberLoginReqDto.email())
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION,
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage()));

        if (!passwordEncoder.matches(memberLoginReqDto.password(), member.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD, ErrorCode.INVALID_PASSWORD.getMessage());
        }

        String accessToken = jwtTokenProvider.generateAccessToken(member);

        // 로컬 회원가입 사용자는 나이/나라 정보가 모두 입력되어 있으므로 isRegistered = true
        boolean isRegistered = isMemberProfileComplete(member);
        return MemberLoginResDto.of(accessToken, isRegistered);
    }

    /**
     * 멤버 정보 조회
     */
    public MemberInfoResDto findById(Long memberId) {
        Member member = findMember(memberId);
        return MemberInfoResDto.from(member);
    }

    /**
     * 멤버 정보 수정
     */
    @Transactional
    public MemberInfoResDto update(Long memberId, MemberUpdateReqDto memberUpdateReqDto) {
        Member member = findMember(memberId);
        member.update(memberUpdateReqDto);
        return MemberInfoResDto.from(member);
    }

    /**
     * 멤버 DB 삭제 (탈퇴)
     */
    @Transactional
    public void delete(Long memberId) {
        Member member = findMember(memberId);
        memberRepository.delete(member);
    }

    /**
     * 공통 - Member 조회 헬퍼 메소드
     */
    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION,
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage()
                ));
    }

    /**
     * 공통 - 프로필 완성 여부 체크 (나이와 국가가 정상적으로 등록되었는가)
     */
    private boolean isMemberProfileComplete(Member member) {
        return member.getAge() > 0 && StringUtils.hasText(member.getCountry());
    }
}