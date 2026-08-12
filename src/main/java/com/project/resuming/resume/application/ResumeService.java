package com.project.resuming.resume.application;

import com.project.resuming.common.exception.BusinessException;
import com.project.resuming.common.response.ErrorCode;
import com.project.resuming.member.domain.Member;
import com.project.resuming.member.domain.repository.MemberRepository;
import com.project.resuming.resume.api.request.ResumeSummaryResDto;
import com.project.resuming.resume.api.response.ResumeInfoListResDto;
import com.project.resuming.resume.api.response.ResumeInfoResDto;
import com.project.resuming.resume.domain.Resume;
import com.project.resuming.resume.domain.repository.ResumeRepository;
import com.project.resuming.selfresume.api.response.SelfResumeInfoResDto;
import com.project.resuming.selfresume.api.response.SelfResumeSummaryResDto;
import com.project.resuming.selfresume.domain.SelfResume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final MemberRepository memberRepository;


    //


    //id로 조회
    public ResumeInfoResDto findById(Long id){
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.RESUME_NOT_FOUND_EXCEPTION,
                        ErrorCode.RESUME_NOT_FOUND_EXCEPTION.getMessage() + id
                ));

        return ResumeInfoResDto.from(resume);
    }

    //전체 조회
    public  List<ResumeSummaryResDto> findAll(Long memberId){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION,
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage()
                ));

        List<ResumeSummaryResDto> resumList = resumeRepository.findByMember(member).stream().map(ResumeSummaryResDto::of).toList();

        return resumList;
    }


}
