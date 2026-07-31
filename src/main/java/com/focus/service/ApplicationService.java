package com.focus.service;

import com.focus.domain.Application;
import com.focus.domain.ApplicationStatus;
import com.focus.domain.CompanyJD;
import com.focus.domain.User;
import com.focus.dto.ApplicationDetailResponseDto;
import com.focus.dto.ApplicationGenerateResponseDto;
import com.focus.dto.ApplicationListResponseDto;
import com.focus.global.exception.CustomException;
import com.focus.global.exception.ErrorCode;
import com.focus.repository.ApplicationRepository;
import com.focus.repository.CompanyJDRepository;
import com.focus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final UserRepository userRepository;
    private final CompanyJDRepository companyJDRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public ApplicationGenerateResponseDto generateApplication(Long userId, Long jdId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        CompanyJD companyJd = companyJDRepository.findById(jdId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST));

        Application application = Application.builder()
                .user(user)
                .companyJd(companyJd)
                .agentAnalysis(null)
                .generatedResume(null)
                .status(ApplicationStatus.PENDING)
                .build();

        applicationRepository.save(application);

        return new ApplicationGenerateResponseDto(
                application.getApplicationId(),
                application.getStatus().name()
        );
    }

    @Transactional(readOnly = true)
    public List<ApplicationListResponseDto> getApplicationList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Application> applications = applicationRepository.findByUserOrderByApplicationIdDesc(user);

        return applications.stream()
                .map(ApplicationListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ApplicationDetailResponseDto getApplicationDetail(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new CustomException(ErrorCode.APPLICATION_NOT_FOUND));

        return new ApplicationDetailResponseDto(application);
    }
}