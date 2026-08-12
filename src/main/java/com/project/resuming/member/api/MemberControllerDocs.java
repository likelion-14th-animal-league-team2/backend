package com.project.resuming.member.api;

import com.project.resuming.common.response.ApiResTemplate;
import com.project.resuming.member.api.request.MemberCompleteProfileReqDto;
import com.project.resuming.member.api.request.MemberLoginReqDto;
import com.project.resuming.member.api.request.MemberSignUpReqDto;
import com.project.resuming.member.api.response.MemberLoginResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.project.resuming.member.api.request.MemberUpdateReqDto;
import com.project.resuming.member.api.response.MemberInfoResDto;


@Tag(name = "Member", description = "회원 관련 API")
public interface MemberControllerDocs {

    @Operation(summary = "카카오 최초 회원가입", description = "소셜 로그인 후 최초 1회 나이와 나라 정보를 입력받아 프로필을 완성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "추가 정보 입력 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 검증 실패"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원")
    })
    ApiResTemplate<MemberInfoResDto> completeProfile(Long memberId, MemberCompleteProfileReqDto reqDto);

    @Operation(summary = "로컬 회원가입", description = "로컬 회원가입")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "이메일 형식 오류")
    })
    ApiResTemplate<Void> localSignUp(MemberSignUpReqDto memberJoinReqDto);

    @Operation(summary = "로컬 로그인", description = "로컬 로그인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "이메일 형식 오류")
    })
    ApiResTemplate<MemberLoginResDto> localLogin(MemberLoginReqDto memberLoginReqDto);

    @Operation(summary = "멤버 id로 찾기", description = "멤버 id로 찾기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버 찾기 성공"),
    })
    ApiResTemplate<MemberInfoResDto> findById(Long memberId);

    @Operation(summary = "멤버 수정하기", description = "멤버 id로 멤버수정합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버 수정 성공"),
            @ApiResponse(responseCode = "404", description = "멤버 없음"),
            @ApiResponse(responseCode = "400", description = "멤버 수정 형식 오류"),
    })
    ApiResTemplate<MemberInfoResDto> update(Long memberId, MemberUpdateReqDto memberUpdateReqDto);

    @Operation(summary = "멤버 삭제하기", description = "멤버 id로 멤버삭제합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "멤버 없음")
    })
    ApiResTemplate<Void> delete(Long memberId);

}
