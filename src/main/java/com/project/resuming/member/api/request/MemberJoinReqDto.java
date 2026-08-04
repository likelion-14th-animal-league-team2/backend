package com.project.resuming.member.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberJoinReqDto(

        @Schema(description = "회원 이름", example = "홍길동")
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @Schema(description = "회원 이메일", example = "test@example.com")
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email

) {
}
