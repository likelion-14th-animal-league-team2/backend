package com.project.resuming.resume.domain;

import com.project.resuming.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private Long resumeId;

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
    public Resume(String strengthAnalysis, String improvementAreas,
                  String personalizedCoachingInsight, String aiRecommendedResumeContent,
                  Member member) {
        this.strengthAnalysis = strengthAnalysis;
        this.improvementAreas = improvementAreas;
        this.personalizedCoachingInsight = personalizedCoachingInsight;
        this.aiRecommendedResumeContent = aiRecommendedResumeContent;
        this.member = member;
    }
}
