package com.project.resuming.resume.infra.ai.service;


import com.project.resuming.common.exception.BusinessException;
import com.project.resuming.common.response.ErrorCode;
import com.project.resuming.member.domain.Member;
import com.project.resuming.member.domain.repository.MemberRepository;
import com.project.resuming.resume.api.request.ResumeAiRecommendRequest;
import com.project.resuming.resume.domain.Resume;
import com.project.resuming.resume.infra.ai.api.dto.request.ResumeAiAnalysisReqDto;
import com.project.resuming.resume.infra.ai.api.dto.response.ResumeAiAnalysisResDto;
import com.project.resuming.resume.domain.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ResumeAiService {

    //외부 ai서버용

    private final ChatClient client; //Builder 사용해서 @RequiredArgsConstructor 사용불가
    private final ResumeRepository resumeRepository;
    private final ImageTextAiExtractionService imageTextAiExtractionService;
    private final MemberRepository memberRepository;
    private final WebClient webClient;


    /*
    프론트 -> 백엔드 전달
    1. 이력서 텍스트
    2. 이력서 이미지 일단 1개만 제출하도록
    3. 공고 텍스트
    4. 공고 이미지 -> 일단 1개만 제출하도록

    흐름 : 프론트 -> 백엔드 -> 백엔드에서 이미지에서 텍스트 추출 -> 텍스트 데이터만 ai로 -> 최종 응답 Json반환 -> 프론트 전달
     */
    @Transactional
    public Resume getResumeAiAdvice(String userText, MultipartFile userImage, String jobText, MultipartFile jobImage, Long memberId){

        //이미지로 텍스트 추출
        String userImageText = imageTextAiExtractionService.extractImageText(userImage);
        String jobImageText = imageTextAiExtractionService.extractImageText(jobImage);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION,
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage()
                ));

        //회원 나라 조회
        String country = member.getCountry();

        //ai 서버요청 dto생성
        ResumeAiAnalysisReqDto resumeAiAnalysisReqDto = ResumeAiAnalysisReqDto.builder()
                .resume(ResumeAiAnalysisReqDto.Resume.builder()
                        .text(userText)
                        .imageText(userImageText)
                        .build())
                .country(country)
                .jobPosting(ResumeAiAnalysisReqDto.JobPosting.builder()
                        .text(jobText)
                        .imageText(jobImageText)
                        .build())
                .build();

        //ai 서버 요청
        ResumeAiAnalysisResDto result = webClient.post()
                .uri("https://ai서버주소")
                .bodyValue(resumeAiAnalysisReqDto)
                .retrieve()
                .bodyToMono(ResumeAiAnalysisResDto.class)
                .block();

        if(result == null){
            throw new BusinessException(
                    ErrorCode.AI_SERVER_EXCEPTION,
                    ErrorCode.AI_SERVER_EXCEPTION.getMessage()
            );
        }

        //최종 반환
        Resume resume = Resume.builder()
                .strengthAnalysis(result.strengthAnalysis())
                .improvementAreas(result.improvementAreas())
                .personalizedCoachingInsight(result.personalizedCoachingInsight())
                .aiRecommendedResumeContent(result.aiRecommendedResumeContent())
                .member(member)
                .build();

        resumeRepository.save(resume);

        return resume;

    }


    //이미지 ->텍스트 추출 테스트 함수
    public String imageTextTest(ResumeAiRecommendRequest request){
        MultipartFile userImage = request.userImage();
        MultipartFile jobImage = request.jobImage();

        String userImageText = imageTextAiExtractionService.extractImageText(userImage);
        String jobImageText = imageTextAiExtractionService.extractImageText(jobImage);
        return userImageText + " ::::::::::: " + jobImageText;

    }









}
