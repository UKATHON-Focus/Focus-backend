package com.focus.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "basic_specs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasicSpec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String school;

    @Column(nullable = false)
    private String major;

    @Column(name = "desired_job", nullable = false)
    private String desiredJob;

    @Column(name = "language_name")
    private String languageName;

    @Column(name = "language_score")
    private String languageScore;

    @Builder
    public BasicSpec(User user, String school, String major, String desiredJob, String languageName, String languageScore) {
        this.user = user;
        this.school = school;
        this.major = major;
        this.desiredJob = desiredJob;
        this.languageName = languageName;
        this.languageScore = languageScore;
    }

    public void update(String school, String major, String desiredJob, String languageName, String languageScore) {
        if (school != null) this.school = school;
        if (major != null) this.major = major;
        if (desiredJob != null) this.desiredJob = desiredJob;
        if (languageName != null) this.languageName = languageName;
        if (languageScore != null) this.languageScore = languageScore;
    }
}