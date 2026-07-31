package com.focus.dto.response;

import com.focus.domain.BasicSpec;
import com.focus.domain.Certificate;
import com.focus.domain.Internship;
import com.focus.domain.User;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record UserProfileResponse(
        String name,
        BasicSpecDto basicSpec,
        List<CertificateDto> certificates,
        List<InternshipDto> internships
) {
    public static UserProfileResponse of(User user, BasicSpec basicSpec, List<Certificate> certificates, List<Internship> internships) {
        return new UserProfileResponse(
                user.getName(),
                BasicSpecDto.from(basicSpec),
                certificates.stream().map(CertificateDto::from).collect(Collectors.toList()),
                internships.stream().map(InternshipDto::from).collect(Collectors.toList())
        );
    }

    public record BasicSpecDto(
            String school,
            String major,
            String desiredJob,
            String languageName,
            String languageScore
    ) {
        public static BasicSpecDto from(BasicSpec spec) {
            if (spec == null) return null;
            return new BasicSpecDto(
                    spec.getSchool(),
                    spec.getMajor(),
                    spec.getDesiredJob(),
                    spec.getLanguageName(),
                    spec.getLanguageScore()
            );
        }
    }

    public record CertificateDto(
            Long certId,
            String certName
    ) {
        public static CertificateDto from(Certificate cert) {
            return new CertificateDto(cert.getId(), cert.getCertName());
        }
    }

    public record InternshipDto(
            Long internId,
            String companyName,
            String role,
            LocalDate startDate,
            LocalDate endDate
    ) {
        public static InternshipDto from(Internship intern) {
            return new InternshipDto(
                    intern.getId(),
                    intern.getCompanyName(),
                    intern.getRole(),
                    intern.getStartDate(),
                    intern.getEndDate()
            );
        }
    }
}