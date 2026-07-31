package com.focus.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ResumeAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private JobPosting jobPosting;

    @Column(columnDefinition = "TEXT")
    private String conflictResolution;

    @Column(columnDefinition = "TEXT")
    private String roleAndContribution;

    @Column(columnDefinition = "TEXT")
    private String hardestPart;

    @Column(columnDefinition = "TEXT")
    private String aiExperience;

    @Column(columnDefinition = "TEXT")
    private String analyzedResult;

    @Builder
    public ResumeAnswer(User user, JobPosting jobPosting, String conflictResolution,
                        String roleAndContribution, String hardestPart, String aiExperience, String analyzedResult) {
        this.user = user;
        this.jobPosting = jobPosting;
        this.conflictResolution = conflictResolution;
        this.roleAndContribution = roleAndContribution;
        this.hardestPart = hardestPart;
        this.aiExperience = aiExperience;
        this.analyzedResult = analyzedResult;
    }
}
