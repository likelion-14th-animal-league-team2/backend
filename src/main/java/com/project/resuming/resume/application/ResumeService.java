package com.project.resuming.resume.application;

import com.project.resuming.common.exception.BusinessException;
import com.project.resuming.common.response.ErrorCode;
import com.project.resuming.member.domain.Member;
import com.project.resuming.member.domain.repository.MemberRepository;
import com.project.resuming.resume.api.response.ResumeInfoListResDto;
import com.project.resuming.resume.api.response.ResumeInfoResDto;
import com.project.resuming.selfresume.api.response.SelfResumeInfoResDto;
import com.project.resuming.selfresume.domain.SelfResume;
import com.project.resuming.selfresume.domain.repository.SelfResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResumeService {

    private final SelfResumeRepository resumeTempRepository;
    private final MemberRepository memberRepository;


    //id로 조회
    public SelfResumeInfoResDto findById(Long id){
        SelfResume resume = resumeTempRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.RESUME_NOT_FOUND_EXCEPTION,
                        ErrorCode.RESUME_NOT_FOUND_EXCEPTION.getMessage() + id
                ));

        return SelfResumeInfoResDto.from(resume);
    }

    //전체 조회
    public ResumeInfoListResDto findAll(Long memberId){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION,
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage()
                ));

        List<ResumeInfoResDto> resumes = resumeTempRepository.findByMember(member).stream().map(ResumeInfoResDto::from).toList();
        return ResumeInfoListResDto.from(resumes);
    }








}
