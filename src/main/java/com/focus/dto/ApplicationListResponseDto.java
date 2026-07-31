package com.focus.dto;

import com.focus.domain.Application;
import lombok.Getter;

@Getter
public class ApplicationListResponseDto {

    private final Long applicationId;
    private final String companyName;
    private final String targetAffiliate;
    private final String jobName;
    private final String status;

    public ApplicationListResponseDto(Application application) {
        this.applicationId = application.getApplicationId();
        this.companyName = application.getCompanyJd().getCompanyName();
        this.targetAffiliate = application.getCompanyJd().getTargetAffiliate();
        this.jobName = application.getCompanyJd().getJobName();
        this.status = application.getStatus().name();
    }
}