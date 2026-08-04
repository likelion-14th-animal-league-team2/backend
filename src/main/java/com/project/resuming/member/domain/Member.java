package com.project.resuming.member.domain;

import com.project.resuming.member.api.request.MemberCompleteProfileReqDto;
import com.project.resuming.member.api.request.MemberUpdateReqDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    private String name;

    private String email;

    private int age;

    private String country;

    private String password;

    @Column(name = "is_profile_completed")
    private boolean profileCompleted; //추후 카톡 로그인에 사용

    @Builder
    public Member(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void completeProfile(MemberCompleteProfileReqDto memberCompleteProfileDto){
        this.age = memberCompleteProfileDto.age();
        this.country = memberCompleteProfileDto.country();
    }

    public void update(MemberUpdateReqDto memberUpdateDto){
        this.age = memberUpdateDto.age();
        this.country = memberUpdateDto.country();
    }

}
