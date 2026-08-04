package com.project.resuming.member.application;

import com.project.resuming.common.exception.BusinessException;
import com.project.resuming.common.response.ErrorCode;
import com.project.resuming.member.api.request.MemberJoinReqDto;
import com.project.resuming.member.api.request.MemberUpdateReqDto;
import com.project.resuming.member.api.response.MemberInfoResDto;
import com.project.resuming.member.domain.Member;
import com.project.resuming.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    //초기 버전(spring security 없는 버전)
    //TODO: 추후에 로그인 기능 구현 시 전부 수정

    //회원가입 임시
    @Transactional
    public void save(MemberJoinReqDto memberJoinReqDto){

        Member member = Member.builder()
                .name(memberJoinReqDto.name())
                .email(memberJoinReqDto.email())
                .build();

        memberRepository.save(member);
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
