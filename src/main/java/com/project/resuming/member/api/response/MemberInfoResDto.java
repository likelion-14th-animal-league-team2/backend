package com.project.resuming.member.api.response;

import com.project.resuming.member.domain.Member;
import lombok.Builder;

@Builder
public record MemberInfoResDto(

        String name,
        String email,
        int age,
        String country

) {

    public static MemberInfoResDto from(Member member){
        return MemberInfoResDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .age(member.getAge())
                .country(member.getCountry())
                .build();
    }


}
