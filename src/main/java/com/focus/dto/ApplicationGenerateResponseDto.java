package com.focus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationGenerateResponseDto {
    private Long applicationId;
    private String status;
}