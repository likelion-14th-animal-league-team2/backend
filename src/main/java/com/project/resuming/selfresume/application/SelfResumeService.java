package com.project.resuming.selfresume.application;

import com.project.resuming.common.exception.BusinessException;
import com.project.resuming.common.response.ErrorCode;
import com.project.resuming.member.domain.Member;
import com.project.resuming.member.domain.repository.MemberRepository;
import com.project.resuming.selfresume.api.response.SelfResumeInfoListResDto;
import com.project.resuming.selfresume.api.response.SelfResumeInfoResDto;
import com.project.resuming.selfresume.api.response.SelfResumeSummaryResDto;
import com.project.resuming.selfresume.domain.SelfResume;
import com.project.resuming.selfresume.domain.repository.SelfResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SelfResumeService {

    private final SelfResumeRepository selfResumeRepository;
    private final MemberRepository memberRepository;

    //selfresume 1개 조회 - resume pk로 조회
    public SelfResumeInfoResDto findById(Long id){
        SelfResume selfResume = selfResumeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.RESUME_NOT_FOUND_EXCEPTION,
                        ErrorCode.RESUME_NOT_FOUND_EXCEPTION.getMessage() + id
                ));

        return SelfResumeInfoResDto.from(selfResume);
    }

    //member별 selfresume전체 조회
    public List<SelfResumeSummaryResDto> findAll(Long memberId){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION,
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage()
                ));

        List<SelfResumeSummaryResDto> resumList = selfResumeRepository.findByMember(member).stream().map(SelfResumeSummaryResDto::of).toList();
        return resumList;
    }








}
