package com.project.resuming.member.api;

import com.project.resuming.common.response.ApiResTemplate;
import com.project.resuming.member.api.request.MemberJoinReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.project.resuming.member.api.request.MemberUpdateReqDto;
import com.project.resuming.member.api.response.MemberInfoResDto;


@Tag(name = "Member", description = "회원 관련 API")
public interface MemberControllerDocs {

    @Operation(summary = "회원가입", description = "이름과 이메일로 신규 회원을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "이메일 형식 오류")
    })
    ApiResTemplate<Void> save(MemberJoinReqDto memberJoinReqDto);

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
