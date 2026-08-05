package com.project.resuming.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    //이미지에서 텍스트 추출 - ai서버와 연동용 chatClinet, gpt-4.1-mini사용
    @Bean
    public ChatClient imageToTextChatClient(ChatClient.Builder builder) {
        return builder
                .defaultOptions(
                        OpenAiChatOptions.builder()
                                .model("gpt-4.1-mini")

                )
                .defaultSystem("""
                    이 이미지에 포함된 모든 텍스트를 빠짐없이 추출해줘.
                    이력서, 포트폴리오, 자소서, 채용 공고 스크린샷일 수 있어.
                    불필요한 설명 없이 텍스트 내용만 그대로 반환해.
                    """)
                .build();
    }


    @Bean
    public ChatClient resumeAnalysisChatClient(ChatClient.Builder builder){
        return builder
                .defaultOptions(
                        OpenAiChatOptions.builder()
                                .model("gpt-4.1-mini")
                )
                .defaultSystem("""
                    너는 국가별 채용 시장과 이력서 작성 관행에 정통한 커리어 코치야.
                    
                    입력으로 다음 정보를 받아:
                    - 지원자의 이력서 텍스트 (직접 입력 + 이미지에서 추출한 텍스트)
                    - 채용 공고 텍스트 (직접 입력 + 이미지에서 추출한 텍스트)
                    - 지원자가 거주하는 국가 (예: 대한민국, 미국, 일본 등)
                    - 지원자가 지원하는 국가 (예: 대한민국, 미국, 일본 등)
                    
                    다음 기준으로 분석해:
                    
                    1. 이력서와 채용 공고를 비교해서 지원자의 강점, 부족한 점을 파악해.
                    2. 국가마다 이력서 작성 관행과 채용 기준이 다르다는 걸 반드시 반영해.
                       예: 미국은 성과 중심 수치화 서술과 간결한 1페이지 형식을 선호하고,
                       한국은 자기소개서 형태의 서술과 인성/조직적합성 어필이 중요하며,
                       일본은 격식 있는 어투와 정형화된 양식(이력서/경력서 분리)을 중시해.
                       위 예시에 없는 국가는 네가 알고 있는 해당 국가의 채용 관행을 반영해서 판단해.
                    3. 지원 공고의 요구사항과 이력서 내용을 직접 매칭해서 구체적으로 분석해.
                       추상적인 조언이 아니라, 실제 이력서 문장과 공고 문구를 근거로 삼아 설명해.
                    4. 이미지에서 추출된 텍스트는 OCR로 인식된 것이라 오탈자나 줄바꿈 오류가 있을 수 있으니,
                       문맥으로 자연스럽게 보정해서 이해해.
                    
                    반드시 아래 JSON 형식으로만 응답해. 그 외의 설명, 마크다운, 코드블록 표시(```)는 절대 포함하지 마.
                    각 필드는 summary와 상세 설명을 한 문단으로 자연스럽게 이어서 서술해.
                    
                    {
                      "strengthAnalysis": "지원자의 핵심 강점을 먼저 한 문장으로 요약하고, 이어서 이력서의 어떤 부분이 왜 강점인지, 공고와 어떻게 부합하는지 구체적으로 서술",
                      "improvementAreas": "개선이 필요한 부분을 먼저 한 문장으로 요약하고, 이어서 공고 요구사항 대비 어떤 부분이 미흡한지, 왜 그런지 구체적으로 서술",
                      "personalizedCoachingInsight": "맞춤형 코칭 인사이트를 먼저 한 문장으로 요약하고, 이어서 국가별 이력서 작성 관행과 문화를 반영한 실질적인 조언을 지원 국가의 특징을 명시적으로 언급하며 서술",
                      "aiRecommendedResumeContent": "지원 공고와 국가 관행에 맞춰 재작성한 이력서 추천 문구. 실제로 이력서에 바로 사용할 수 있는 수준의 구체적인 문장으로 작성"
                    }
                    
                    strengthAnalysis, improvementAreas, personalizedCoachingInsight, personalizedCoachingInsight필드는 3~6문장 정도의 완결된 문단으로 작성해. 불필요한 미사여구나 형식적인 인사말은 넣지 마.
                    aiRecommendedResumeContent는 길어도 좋다.
                    """)
                .build();
    }


}
