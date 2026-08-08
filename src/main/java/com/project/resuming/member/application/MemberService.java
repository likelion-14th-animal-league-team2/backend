package com.project.resuming.member.application;

import com.project.resuming.common.exception.BusinessException;
import com.project.resuming.common.response.ErrorCode;
import com.project.resuming.member.api.request.MemberLoginReqDto;
import com.project.resuming.member.api.request.MemberSignUpReqDto;
import com.project.resuming.member.api.request.MemberUpdateReqDto;
import com.project.resuming.member.api.response.MemberInfoResDto;
import com.project.resuming.member.api.response.MemberLoginResDto;
import com.project.resuming.member.domain.Member;
import com.project.resuming.member.domain.repository.MemberRepository;
import com.project.resuming.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    //초기 버전(spring security 없는 버전)
    //TODO: 추후에 로그인 기능 구현 시 전부 수정 - 카카오 소셜 로그인 추후

    //로컬 회원가입
    @Transactional
    public void localSignUp(MemberSignUpReqDto memberJoinReqDto){

        // 존재하는 이메일인지 확인
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
                .profileCompleted(true)
                .build();

        memberRepository.save(member);
    }

    //로컬 로그인
    public MemberLoginResDto localLogin(MemberLoginReqDto memberLoginReqDto){

        Member member = memberRepository.findByEmail(memberLoginReqDto.email())
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION,
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage()));

        if (!passwordEncoder.matches(memberLoginReqDto.password(), member.getPassword())){
            throw new BusinessException(ErrorCode.INVALID_PASSWORD, ErrorCode.INVALID_PASSWORD.getMessage());
        }

        String accessToken = jwtTokenProvider.generateAccessToken(member);
        return MemberLoginResDto.from(accessToken);

    }


    //멤버 정보 조회
    public MemberInfoResDto findById(Long memberId){
        Member member = findMember(memberId);
        return MemberInfoResDto.from(member);
    }

    //멤버 정보 수정
    @Transactional
    public MemberInfoResDto update(Long memberId, MemberUpdateReqDto memberUpdateReqDto){
        Member member = findMember(memberId);
        member.update(memberUpdateReqDto);
        return MemberInfoResDto.from(member);
    }

    //멤버 db에서 삭제
    @Transactional
    public void delete(Long memberId){
        Member member = findMember(memberId);
        memberRepository.delete(member);
    }


    //멤버 찾기 메서드
    private Member findMember(Long memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION,
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage()
                ));
    }




}
