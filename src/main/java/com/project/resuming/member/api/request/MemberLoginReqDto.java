package com.project.resuming.member.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberLoginReqDto(

        @Schema(description = "회원 이메일", example = "test@example.com")
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,

        @Schema(description = "회원 비밀번호", example = "규칙없음")
        @NotBlank(message = "비밀번호는 필수입니다.")
        String password

) {
}
