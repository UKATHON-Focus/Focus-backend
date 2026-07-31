package com.focus.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResumeRequestDto {
    private Long userId;
    private Long companyJDId;
    private String conflictResolution;
    private String roleAndContribution;
    private String hardestPart;
    private String aiExperience;
}