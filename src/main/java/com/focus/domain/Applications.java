package com.focus.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Applications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jd_id")
    private CompanyJD companyJd;

    @Column(columnDefinition = "TEXT")
    private String agentAnalysis;

    @Column(columnDefinition = "TEXT")
    private String generatedResume;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Builder
    public Applications(Member member, CompanyJD companyJd, String agentAnalysis, String generatedResume, ApplicationStatus status) {
        this.member = member;
        this.companyJd = companyJd;
        this.agentAnalysis = agentAnalysis;
        this.generatedResume = generatedResume;
        this.status = status;
    }
}
