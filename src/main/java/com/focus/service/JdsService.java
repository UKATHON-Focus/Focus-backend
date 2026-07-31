package com.focus.service;

import com.focus.domain.CompanyJD;
import com.focus.dto.JdsResponseDto;
import com.focus.dto.JdsResponseDto.AffiliateDto;
import com.focus.dto.JdsResponseDto.CompanyDto;
import com.focus.dto.JdsResponseDto.JobDto;
import com.focus.repository.CompanyJDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JdsService {

    private final CompanyJDRepository companyJDRepository;

    public JdsResponseDto getJobDropdownList() {
        List<CompanyJD> all = companyJDRepository.findAll();

        // 1단계: 회사명(companyName)으로 그룹핑
        Map<String, List<CompanyJD>> byCompany = all.stream()
                .collect(Collectors.groupingBy(CompanyJD::getCompanyName));

        List<CompanyDto> companies = byCompany.entrySet().stream()
                .map(companyEntry -> {
                    // 2단계: 같은 회사 안에서 계열사(targetAffiliate)로 그룹핑 (null 허용)
                    Map<String, List<CompanyJD>> byAffiliate = companyEntry.getValue().stream()
                            .collect(Collectors.groupingBy(jd ->
                                    jd.getTargetAffiliate() == null ? "" : jd.getTargetAffiliate()));

                    List<AffiliateDto> affiliates = byAffiliate.entrySet().stream()
                            .map(affiliateEntry -> {
                                List<JobDto> jobs = affiliateEntry.getValue().stream()
                                        .map(jd -> new JobDto(jd.getJdId(), jd.getJobName()))
                                        .collect(Collectors.toList());

                                String affiliateName = affiliateEntry.getKey().isEmpty()
                                        ? null
                                        : affiliateEntry.getKey();

                                return new AffiliateDto(affiliateName, jobs);
                            })
                            .collect(Collectors.toList());

                    return new CompanyDto(companyEntry.getKey(), affiliates);
                })
                .collect(Collectors.toList());

        return new JdsResponseDto(companies);
    }
}
