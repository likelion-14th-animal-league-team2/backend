package com.project.resuming.member.domain;

import com.project.resuming.member.api.request.MemberCompleteProfileReqDto;
import com.project.resuming.member.api.request.MemberUpdateReqDto;
import com.project.resuming.selfresume.domain.SelfResume;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    private String name;

    private int age;

    private String country;

    private String email;

    private String password;

    @Column(name = "is_profile_completed")
    private boolean profileCompleted; //추후 카톡 로그인에 사용

    @OneToMany(mappedBy ="member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<SelfResume> selfResumes = new ArrayList<>();

    @Builder
    public Member(String name, int age, String country, String email, String password, boolean profileCompleted) {
        this.name = name;
        this.age = age;
        this.country = country;
        this.email = email;
        this.password = password;
        this.profileCompleted = profileCompleted;
    }

    public void update(MemberUpdateReqDto memberUpdateDto){
        this.age = memberUpdateDto.age();
        this.country = memberUpdateDto.country();
    }

    public void completeProfile(Integer age, String country) {
        this.age = age;
        this.country = country;
    }

}
