package com.focus.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName; // KAKAO, TOSS
    private String position;    // 백엔드 개발자

    @Column(columnDefinition = "TEXT")
    private String requirements; // 직무 요건 및 인재상

    @Builder
    public JobPosting(String companyName, String position, String requirements) {
        this.companyName = companyName;
        this.position = position;
        this.requirements = requirements;
    }
}
