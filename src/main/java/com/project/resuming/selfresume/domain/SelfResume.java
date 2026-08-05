package com.project.resuming.selfresume.domain;

import com.project.resuming.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelfResume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "self_resume_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String strengthAnalysis;

    @Column(columnDefinition = "TEXT")
    private String improvementAreas;

    @Column(columnDefinition = "TEXT")
    private String personalizedCoachingInsight;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String aiRecommendedResumeContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public SelfResume(String strengthAnalysis, String improvementAreas,
                  String personalizedCoachingInsight, String aiRecommendedResumeContent,
                  Member member) {
        this.strengthAnalysis = strengthAnalysis;
        this.improvementAreas = improvementAreas;
        this.personalizedCoachingInsight = personalizedCoachingInsight;
        this.aiRecommendedResumeContent = aiRecommendedResumeContent;
        this.member = member;
    }
}
