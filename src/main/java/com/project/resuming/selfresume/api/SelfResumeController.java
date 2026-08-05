package com.project.resuming.selfresume.api;

import com.project.resuming.common.response.ApiResTemplate;
import com.project.resuming.common.response.SuccessCode;
import com.project.resuming.resume.api.request.ResumeAiRecommendRequest;
import com.project.resuming.resume.domain.Resume;
import com.project.resuming.selfresume.api.request.SelfResumeAiRecommendRequest;
import com.project.resuming.selfresume.api.response.SelfResumeInfoListResDto;
import com.project.resuming.selfresume.api.response.SelfResumeInfoResDto;
import com.project.resuming.selfresume.application.SelfResumeService;
import com.project.resuming.selfresume.infra.service.SelfResumeAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/selfresume")
public class SelfResumeController {

    private final SelfResumeService resumeService;
    private final SelfResumeAiService resumeAiService;

    //resume Id조회
    @GetMapping("{id}")
    public ApiResTemplate<SelfResumeInfoResDto> findById(@PathVariable(name = "id") Long resumeId){
        SelfResumeInfoResDto resume = resumeService.findById(resumeId);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS,resume);
    }

    @GetMapping("/all")
    public ApiResTemplate<SelfResumeInfoListResDto> findAll(@RequestParam(name = "memberId")Long memberId){
        SelfResumeInfoListResDto all = resumeService.findAll(memberId);
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
    @PostMapping(value = "/ai", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResTemplate<SelfResumeInfoResDto> resumeAiRecommend(
            @ModelAttribute SelfResumeAiRecommendRequest request, @RequestParam(name = "memberId") Long memberId
    ) {
        SelfResumeInfoResDto resumeAiAdvice = resumeAiService.getResumeAiAdvice(request, memberId);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, resumeAiAdvice);
    };



}





