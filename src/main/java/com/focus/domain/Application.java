package com.focus.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "applications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;   // ← id 에서 applicationId 로 변경 (getApplicationId() 생성됨)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jd_id", nullable = false)
    private CompanyJD companyJd;

    @Column(name = "agent_analysis", columnDefinition = "TEXT")
    private String agentAnalysis;

    @Column(name = "generated_resume", columnDefinition = "TEXT")
    private String generatedResume;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Builder
    public Application(User user, CompanyJD companyJd, String agentAnalysis, String generatedResume, ApplicationStatus status) {
        this.user = user;
        this.companyJd = companyJd;
        this.agentAnalysis = agentAnalysis;
        this.generatedResume = generatedResume;
        this.status = status;
    }
}
