package com.project.resuming.selfresume.api;

import com.project.resuming.common.response.ApiResTemplate;
import com.project.resuming.common.response.SuccessCode;
import com.project.resuming.selfresume.api.request.SelfResumeAiAnalysisRequest;
import com.project.resuming.selfresume.api.response.SelfResumeInfoListResDto;
import com.project.resuming.selfresume.api.response.SelfResumeInfoResDto;
import com.project.resuming.selfresume.application.SelfResumeService;
import com.project.resuming.selfresume.infra.service.SelfResumeAiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/selfresume")
@Tag(name = "selfresume api", description = "백엔드 자체적으로 Llm요청하는 버전의 api입니다. ")
public class SelfResumeController {

    private final SelfResumeService resumeService;
    private final SelfResumeAiService resumeAiService;

    //selfresume Id조회
    @GetMapping("{id}")
    @Operation(summary = "Id로 selfresume조회", description = "selfresume pk로 resume 1개를 찾습니다")
    public ApiResTemplate<SelfResumeInfoResDto> findById(@PathVariable(name = "id") Long resumeId){
        SelfResumeInfoResDto resume = resumeService.findById(resumeId);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS,resume);
    }

    @GetMapping("/all")
    @Operation(summary = "selfresume 전체 조회", description = "멤버가 가지고 있는 selfresume전체를 찾습니다. List반환")
    public ApiResTemplate<SelfResumeInfoListResDto> findAll(@RequestParam(name = "memberId")Long memberId){
        SelfResumeInfoListResDto resumeInfoListRes = resumeService.findAll(memberId);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, resumeInfoListRes);
    }

    @Operation(summary = "ai가 이력서, 자소서를 분석합니다.", description = "이력서 제출")
    @PostMapping(value = "/ai", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResTemplate<SelfResumeInfoResDto> resumeAiRecommend(
            @ModelAttribute SelfResumeAiAnalysisRequest request, @RequestParam(name = "memberId") Long memberId
    ) {
        SelfResumeInfoResDto resumeAiAdvice = resumeAiService.getResumeAiAdvice(request, memberId);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, resumeAiAdvice);
    };



}





