package com.project.resuming.resume.api;

import com.project.resuming.common.response.ApiResTemplate;
import com.project.resuming.common.response.SuccessCode;
import com.project.resuming.resume.api.request.ResumeAiAnalysisRequest;
import com.project.resuming.resume.api.response.ResumeInfoListResDto;
import com.project.resuming.resume.api.response.ResumeInfoResDto;
import com.project.resuming.resume.application.ResumeService;
import com.project.resuming.resume.infra.ai.service.ResumeAiService;
import com.project.resuming.selfresume.api.response.SelfResumeInfoResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/resume")
@Tag(name = "resume API", description = "resume관련 api - ai서버가 llm에 요청보내느 버전의 api ")
public class ResumeController {

    private final ResumeService resumeService;
    private final ResumeAiService resumeAiService;

    //resume Id조회
    @GetMapping("{id}")
    @Operation(summary = "Id로 resume조회", description = "resume pk로 resume 1개를 찾습니다")
    public ApiResTemplate<ResumeInfoResDto> findById(@PathVariable(name = "id") Long resumeId){
        ResumeInfoResDto resume = resumeService.findById(resumeId);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS,resume);
    }

    //resume 전체 조회
    @GetMapping("/all")
    @Operation(summary = "resume 전체 조회", description = "멤버가 가지고 있는 resume전체를 찾습니다. List반환")
    public ApiResTemplate<ResumeInfoListResDto> findAll(@RequestParam(name = "memberId")Long memberId){
        ResumeInfoListResDto all = resumeService.findAll(memberId);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, all);
    }


    //추후 ai서버 생성되면 전부 수정
    @PostMapping(value = "/imagetext", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResTemplate<String> resumeAiRecommend(
            @ModelAttribute ResumeAiAnalysisRequest request
    ) {
        String s = resumeAiService.imageTextTest(request);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, s);
    };



}





