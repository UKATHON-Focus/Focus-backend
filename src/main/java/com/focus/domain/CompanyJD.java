package com.focus.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CompanyJD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jdId;

    private String companyName;
    private String targetAffiliate;
    private String jobName;

    @Column(columnDefinition = "TEXT")
    private String requirements;
    private String preferredSkills;
    private String coreValues;

    @Builder
    public CompanyJD(String companyName, String targetAffiliate, String jobName, String requirements, String preferredSkills, String coreValues) {
        this.companyName = companyName;
        this.targetAffiliate = targetAffiliate;
        this.jobName = jobName;
        this.requirements = requirements;
        this.preferredSkills = preferredSkills;
        this.coreValues = coreValues;
    }
}
