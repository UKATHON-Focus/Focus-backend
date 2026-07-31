package com.focus.dto.request;

import com.focus.domain.BasicSpec;
import com.focus.domain.Certificate;
import com.focus.domain.Internship;
import com.focus.domain.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record UserProfileRequest(
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotNull(message = "기본 스펙 정보는 필수입니다.")
        @Valid
        BasicSpecDto basicSpec,

        List<CertificateDto> certificates,

        @Valid
        List<InternshipDto> internships
) {
    public record BasicSpecDto(
            @NotBlank String school,
            @NotBlank String major,
            @NotBlank String desiredJob,
            String languageName,
            String languageScore
    ) {
        public BasicSpec toEntity(User user) {
            return BasicSpec.builder()
                    .user(user)
                    .school(school())
                    .major(major())
                    .desiredJob(desiredJob())
                    .languageName(languageName())
                    .languageScore(languageScore())
                    .build();
        }
    }

    public record CertificateDto(
            @NotBlank String certName
    ) {
        public Certificate toEntity(User user) {
            return Certificate.builder()
                    .user(user)
                    .certName(certName())
                    .build();
        }
    }

    public record InternshipDto(
            @NotBlank String companyName,
            @NotBlank String role,
            @NotNull LocalDate startDate,
            @NotNull LocalDate endDate
    ) {
        public Internship toEntity(User user) {
            return Internship.builder()
                    .user(user)
                    .companyName(companyName())
                    .role(role())
                    .startDate(startDate())
                    .endDate(endDate())
                    .build();
        }
    }
}
