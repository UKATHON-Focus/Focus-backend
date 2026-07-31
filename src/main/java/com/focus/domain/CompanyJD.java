package com.focus.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "company_jd")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyJD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Id -> id 로 수정

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "target_affiliate")
    private String targetAffiliate;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Column(name = "requirements", columnDefinition = "TEXT", nullable = false)
    private String requirements;

    @Column(name = "preferred_skills", nullable = false)
    private String preferredSkills;

    @Column(name = "core_value", nullable = false)
    private String coreValue;

    @Builder
    public CompanyJD(String companyName, String targetAffiliate, String jobName, String requirements, String preferredSkills, String coreValue) {
        this.companyName = companyName;
        this.targetAffiliate = targetAffiliate;
        this.jobName = jobName;
        this.requirements = requirements;
        this.preferredSkills = preferredSkills;
        this.coreValue = coreValue;
    }
}