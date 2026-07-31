package com.focus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class JdsResponseDto {
    private List<CompanyDto> companies;

    @Getter
    @AllArgsConstructor
    public static class CompanyDto {
        private String companyName;             // 예: 카카오
        private List<AffiliateDto> affiliates;  // 계열사 목록 (없으면 targetAffiliate=null 그룹 하나만 존재)
    }

    @Getter
    @AllArgsConstructor
    public static class AffiliateDto {
        private String affiliateName; // null이면 계열사 구분 없는 본사 직무
        private List<JobDto> jobs;
    }

    @Getter
    @AllArgsConstructor
    public static class JobDto {
        private Long jdId;      // 이후 지원(Application) 생성 시 사용할 ID
        private String jobName;
    }
}
