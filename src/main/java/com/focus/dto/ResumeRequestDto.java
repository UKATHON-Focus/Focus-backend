package com.focus.dto;

import lombok.Getter;

@Getter
public class ResumeRequestDto {
    private Long memberId;
    private Long jobPostingId;
    private String conflictResolution;
    private String roleAndContribution;
    private String hardestPart;
    private String aiExperience;
}
