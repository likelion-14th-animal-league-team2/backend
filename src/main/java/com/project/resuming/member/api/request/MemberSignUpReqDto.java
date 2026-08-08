package com.project.resuming.member.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record MemberSignUpReqDto(

        @Schema(description = "회원 이름", example = "홍길동")
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @Schema(description = "회원 나이", example = "3")
        @Min(5)
        @Max(100)
        int age,

        @Schema(description = "거주 국가", example = "대한민국")
        @NotBlank(message = "거주국가는 필수입니다.")
        String country,

        @Schema(description = "회원 이메일", example = "test@example.com")
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,

        @Schema(description = "회원 비밀번호", example = "규칙없음")
        @NotBlank(message = "비밀번호는 필수입니다.")
        String password





) {
}
