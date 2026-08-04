package com.project.resuming.member.api.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record MemberCompleteProfileReqDto(

        @Min(value = 5, message = "나이는 5 이상이어야 합니다.")
        @Max(value = 110, message = "나이는 110 이하여야 합니다.")
        int age,

        @NotBlank(message = "국가는 필수입니다.")
        String country

) {
}
