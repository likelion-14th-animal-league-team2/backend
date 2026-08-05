package com.project.resuming.selfresume.infra.service;


import com.project.resuming.common.exception.BusinessException;
import com.project.resuming.common.response.ErrorCode;
import com.project.resuming.member.domain.Member;
import com.project.resuming.member.domain.repository.MemberRepository;
import com.project.resuming.selfresume.api.request.SelfResumeAiAnalysisRequest;
import com.project.resuming.selfresume.api.response.SelfResumeInfoResDto;
import com.project.resuming.selfresume.domain.SelfResume;
import com.project.resuming.selfresume.domain.repository.SelfResumeRepository;
import com.project.resuming.selfresume.infra.api.dto.response.SelfResumeAiAnalysisResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SelfResumeAiService {

    //스프링 서버 스스로 요청 보내는 버전

    private final ChatClient resumeAnalysisChatClient;
    private final SelfResumeRepository selfResumeRepository;
    private final ImageTextAiExtractionService2 imageTextAiExtractionService;
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
    public SelfResumeInfoResDto getResumeAiAdvice(SelfResumeAiAnalysisRequest request, Long memberId){

        //이미지로 텍스트 추출
        String resumeImageText = imageTextAiExtractionService.extractImageText(request.resumeImage());
        String jobImageText = imageTextAiExtractionService.extractImageText(request.jobImage());

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION,
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage()
                ));

        //회원 나라 조회
        String currentCountry = member.getCountry();
        //타켓 나라
        String targetCountry = request.targetCountry();


        //유저 메시지(프롬프트) 구성
        String userMessage = buildUserMessage(
                request.resumeText(),
                resumeImageText,
                request.jobText(),
                jobImageText,
                currentCountry,
                targetCountry
        );

        // 4. ChatClient 호출 + 응답을 DTO로 바로 매핑
        SelfResumeAiAnalysisResDto result = resumeAnalysisChatClient
                .prompt()
                .user(userMessage)
                .call()
                .entity(SelfResumeAiAnalysisResDto.class);

        if (result == null) {
            throw new BusinessException(
                    ErrorCode.AI_SERVER_EXCEPTION,
                    ErrorCode.AI_SERVER_EXCEPTION.getMessage()
            );
        }

        // 5. 최종 저장
        SelfResume selfresume = SelfResume.builder()
                .strengthAnalysis(result.strengthAnalysis())
                .improvementAreas(result.improvementAreas())
                .personalizedCoachingInsight(result.personalizedCoachingInsight())
                .aiRecommendedResumeContent(result.aiRecommendedResumeContent())
                .member(member)
                .build();

        selfResumeRepository.save(selfresume);

        return SelfResumeInfoResDto.from(selfresume);



    }

    private String buildUserMessage(
            String userText,
            String userImageText,
            String jobText,
            String jobImageText,
            String currentCountry,
            String targetCountry
    ){
        return """
                [지원자 거주 국가]
                %s

                [지원 대상 국가]
                %s

                [이력서 - 직접 입력 텍스트]
                %s

                [이력서 - 이미지에서 추출한 텍스트]
                %s

                [채용 공고 - 직접 입력 텍스트]
                %s

                [채용 공고 - 이미지에서 추출한 텍스트]
                %s
                """.formatted(
                nullToEmpty(currentCountry),
                nullToEmpty(targetCountry),
                nullToEmpty(userText),
                nullToEmpty(userImageText),
                nullToEmpty(jobText),
                nullToEmpty(jobImageText)
        );


    }

    private String nullToEmpty(String value) {
        return (value == null || value.isBlank()) ? "(정보 없음)" : value;
    }



    //이미지 ->텍스트 추출 테스트 함수
    public String imageTextTest(SelfResumeAiAnalysisRequest request){
        MultipartFile userImage = request.resumeImage();
        MultipartFile jobImage = request.jobImage();

        String userImageText = imageTextAiExtractionService.extractImageText(userImage);
        String jobImageText = imageTextAiExtractionService.extractImageText(jobImage);
        return userImageText + " ::::::::::: " + jobImageText;

    }









}
