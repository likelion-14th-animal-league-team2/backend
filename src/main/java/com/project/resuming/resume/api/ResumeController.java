package com.project.resuming.resume.api;

import com.project.resuming.common.response.ApiResTemplate;
import com.project.resuming.common.response.SuccessCode;
import com.project.resuming.resume.api.request.ResumeAiRecommendRequest;
import com.project.resuming.resume.api.response.ResumeInfoListResDto;
import com.project.resuming.resume.api.response.ResumeInfoResDto;
import com.project.resuming.resume.application.ResumeService;
import com.project.resuming.resume.infra.ai.service.ResumeAiService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService resumeService;
    private final ResumeAiService resumeAiService;

    //resume Id조회
    @GetMapping("{id}")
    public ApiResTemplate<ResumeInfoResDto> findById(@PathVariable(name = "id") Long resumeId){
        ResumeInfoResDto resume = resumeService.findById(resumeId);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS,resume);
    }

    @GetMapping("/all")
    public ApiResTemplate<ResumeInfoListResDto> findAll(@RequestParam(name = "memberId")Long memberId){
        ResumeInfoListResDto all = resumeService.findAll(memberId);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, all);
    }





//    //한 번에 서용자(텍스트, 이미지), 공고(텍스트, 이미지) 받는 버전
//    @PostMapping(value = "/first", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ApiResTemplate<Void> resumeAiRecommend(
//            @RequestPart(value = "userText", required = false) String userText,
//            @RequestPart(value = "userImage", required = false) MultipartFile userImage,
//            @RequestPart(value = "jobText", required = false) String jobText,
//            @RequestPart(value = "jobImage", required = false) MultipartFile jobImage
//    ){
//        resumeAiService.getResumeAiAdvice(userText, userImage, jobText, jobImage);
//    };

    //
    @PostMapping(value = "/imagetext", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResTemplate<String> resumeAiRecommend(
            @ModelAttribute ResumeAiRecommendRequest request
    ) {
        String s = resumeAiService.imageTextTest(request);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, s);
    };



}





