package com.project.resuming.resume.infra.ai.service;


import com.project.resuming.common.exception.BusinessException;
import com.project.resuming.common.response.ErrorCode;
import com.project.resuming.member.domain.Member;
import com.project.resuming.member.domain.repository.MemberRepository;
import com.project.resuming.resume.api.request.ResumeToBackReqDto;
import com.project.resuming.resume.api.response.ResumeInfoResDto;
import com.project.resuming.resume.domain.Resume;
import com.project.resuming.resume.infra.ai.api.dto.request.ResumeToAIReqDto;
import com.project.resuming.resume.infra.ai.api.dto.response.ResumeAiAnalysisResDto;
import com.project.resuming.resume.domain.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;



@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ResumeAiService {

    //외부 ai서버용
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
    5. 지원 회사 이름, 프로젝트

    흐름 : 프론트 -> 백엔드 -> 백엔드에서 이미지에서 텍스트 추출 -> 텍스트 데이터만 ai로 -> 최종 응답 Json반환 -> 프론트 전달
     */
    @Transactional
    public ResumeInfoResDto aiAnalysis(ResumeToBackReqDto reqDto, Long memberId){

        //이미지로 텍스트 추출
        String resumeImageText = imageTextAiExtractionService.extractImageText(reqDto.resumeImage());
        String jobImageText = imageTextAiExtractionService.extractImageText(reqDto.jobImage());

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION,
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage()
                ));

        //회원 나라 조회
        String country = member.getCountry();

        //ai 서버요청 dto생성
        ResumeToAIReqDto resumeAiAnalysisReqDto = ResumeToAIReqDto.builder()
                .resume(ResumeToAIReqDto.Resume.builder()
                        .text(reqDto.resumeText())
                        .imageText(resumeImageText)
                        .build())
                .currentCountry(country)
                .targetCompany(reqDto.targetCompany())
                .jobPosting(ResumeToAIReqDto.JobPosting.builder()
                        .text(reqDto.jobText())
                        .imageText(jobImageText)
                        .targetCountry(reqDto.targetCountry())
                        .build())
                .build();

        //ai 서버 요청
        ResumeAiAnalysisResDto result = webClient.post()
                .uri("https://ai-61pg.onrender.com/ai/resume-localize")
                .bodyValue(resumeAiAnalysisReqDto)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response ->
                        response.bodyToMono(String.class).map(body -> {
                            System.out.println("AI 서버 에러 응답: " + body);
                            return new RuntimeException("AI 서버 400 에러: " + body);
                        })
                )
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
                .targetCompany(result.targetCompany())
                .member(member)
                .build();

        resumeRepository.save(resume);

        return ResumeInfoResDto.from(resume);

    }


    //이미지 ->텍스트 추출 테스트 함수
    public String imageTextTest(ResumeToBackReqDto request){
        MultipartFile resumeImage = request.resumeImage();
        MultipartFile jobImage = request.jobImage();

        String userImageText = imageTextAiExtractionService.extractImageText(resumeImage);
        String jobImageText = imageTextAiExtractionService.extractImageText(jobImage);
        return userImageText + " ::::::::::: " + jobImageText;

    }









}
