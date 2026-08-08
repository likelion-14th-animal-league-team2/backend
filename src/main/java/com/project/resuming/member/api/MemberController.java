package com.project.resuming.member.api;

import com.project.resuming.common.response.ApiResTemplate;
import com.project.resuming.common.response.SuccessCode;
import com.project.resuming.member.api.request.MemberLoginReqDto;
import com.project.resuming.member.api.request.MemberSignUpReqDto;
import com.project.resuming.member.api.request.MemberUpdateReqDto;
import com.project.resuming.member.api.response.MemberInfoResDto;
import com.project.resuming.member.api.response.MemberLoginResDto;
import com.project.resuming.member.application.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;

    //로컬 회원가입
    @PostMapping("/localsignup")
    public ApiResTemplate<Void> localSignUp(@RequestBody @Valid MemberSignUpReqDto memberSignUpReqDto){
        memberService.localSignUp(memberSignUpReqDto);
        return ApiResTemplate.successWithNoContent(SuccessCode.MEMBER_SAVE_SUCCESS);
    }

    //로컬 로그인
    @PostMapping("/locallogin")
    public ApiResTemplate<MemberLoginResDto> localLogin(@RequestBody @Valid MemberLoginReqDto memberLoginReqDto){
        MemberLoginResDto memberLoginResDto = memberService.localLogin(memberLoginReqDto);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, memberLoginResDto);

    }

    //member 1명 찾기
    @GetMapping()
    public ApiResTemplate<MemberInfoResDto> findById(@AuthenticationPrincipal Long memberId){
        MemberInfoResDto memberInfoResDto = memberService.findById(memberId);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, memberInfoResDto);
    }

    @PatchMapping()
    public ApiResTemplate<MemberInfoResDto> update(@AuthenticationPrincipal Long memberId, @RequestBody @Valid MemberUpdateReqDto memberUpdateReqDto){
        MemberInfoResDto memberInfoResDto = memberService.update(memberId, memberUpdateReqDto);
        return ApiResTemplate.successResponse(SuccessCode.MEMBER_UPDATE_SUCCESS, memberInfoResDto);
    }

    @DeleteMapping()
    public ApiResTemplate<Void> delete(@AuthenticationPrincipal Long memberId){
        memberService.delete(memberId);
        return ApiResTemplate.successWithNoContent(SuccessCode.MEMBER_DELETE_SUCCESS);
    }


}
