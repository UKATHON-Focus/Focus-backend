package com.focus.dto.request;

import com.focus.domain.Certificate;
import com.focus.domain.Internship;
import com.focus.domain.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record UserProfileUpdateRequest(
        String name,
        BasicSpecUpdateDto basicSpec,
        @Valid List<CertificateUpdateDto> certificates,
        @Valid List<InternshipUpdateDto> internships
) {
    public record BasicSpecUpdateDto(
            String school,
            String major,
            String desiredJob,
            String languageName,
            String languageScore
    ) {}

    public record CertificateUpdateDto(
            Long certId,
            @NotBlank(message = "자격증 이름은 필수입니다.") String certName
    ) {
        public Certificate toEntity(User user) {
            return Certificate.builder().user(user).certName(certName()).build();
        }
    }

    public record InternshipUpdateDto(
            Long internId,
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