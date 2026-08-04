package com.project.resuming.member.api;

import com.project.resuming.common.response.ApiResTemplate;
import com.project.resuming.common.response.SuccessCode;
import com.project.resuming.member.api.request.MemberJoinReqDto;
import com.project.resuming.member.api.request.MemberUpdateReqDto;
import com.project.resuming.member.api.response.MemberInfoResDto;
import com.project.resuming.member.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;

    @PostMapping()
    public ApiResTemplate<Void> save(@RequestBody @Valid MemberJoinReqDto memberJoinReqDto){
        memberService.save(memberJoinReqDto);
        return ApiResTemplate.successWithNoContent(SuccessCode.MEMBER_SAVE_SUCCESS);
    }

    @GetMapping("/{memberId}")
    public ApiResTemplate<MemberInfoResDto> findById(@PathVariable(name = "memberId")Long memberId){
        MemberInfoResDto memberInfoResDto = memberService.findById(memberId);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, memberInfoResDto);
    }

    @PatchMapping("/{memberId}")
    public ApiResTemplate<MemberInfoResDto> update(@PathVariable(name = "memberId")Long memberId, @RequestBody @Valid MemberUpdateReqDto memberUpdateReqDto){
        MemberInfoResDto memberInfoResDto = memberService.update(memberId, memberUpdateReqDto);
        return ApiResTemplate.successResponse(SuccessCode.MEMBER_UPDATE_SUCCESS, memberInfoResDto);
    }

    @DeleteMapping("/{memberId}")
    public ApiResTemplate<Void> delete(@PathVariable(name = "memberId")Long memberId){
        memberService.delete(memberId);
        return ApiResTemplate.successWithNoContent(SuccessCode.MEMBER_DELETE_SUCCESS);
    }


}
