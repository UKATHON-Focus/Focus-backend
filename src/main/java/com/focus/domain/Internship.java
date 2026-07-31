package com.focus.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "internships")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Internship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String role;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Builder
    public Internship(User user, String companyName, String role, LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.companyName = companyName;
        this.role = role;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}