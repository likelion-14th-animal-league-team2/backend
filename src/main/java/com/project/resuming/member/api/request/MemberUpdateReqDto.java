package com.project.resuming.member.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record MemberUpdateReqDto(

        @Schema(description = "회원 나이", example = "20")
        @Min(value = 5, message = "나이는 5 이상이어야 합니다.")
        @Max(value = 110, message = "나이는 110 이하여야 합니다.")
        int age,

        @Schema(description = "회원 국가", example = "대한민국")
        @NotBlank(message = "국가는 필수입니다.")
        String country


) {
}
