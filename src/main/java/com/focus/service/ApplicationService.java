package com.focus.service;

import com.focus.client.GptResearcherClient;
import com.focus.domain.*;
import com.focus.dto.ApplicationDetailResponseDto;
import com.focus.dto.ApplicationGenerateResponseDto;
import com.focus.dto.ApplicationListResponseDto;
import com.focus.global.exception.CustomException;
import com.focus.global.exception.ErrorCode;
import com.focus.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final UserRepository userRepository;
    private final CompanyJDRepository companyJDRepository;
    private final ApplicationRepository applicationRepository;
    private final BasicSpecRepository basicSpecRepository;
    private final ExperienceRepository experienceRepository;
    private final GptResearcherClient gptResearcherClient;

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

        runGeneration(application, userId, companyJd);

        return new ApplicationGenerateResponseDto(
                application.getApplicationId(),
                application.getStatus().name()
        );
    }

    private void runGeneration(Application application, Long userId, CompanyJD companyJd) {
        try {
            application.updateStatus(ApplicationStatus.ANALYZING);

            BasicSpec spec = basicSpecRepository.findByUserId(userId)
                    .orElseThrow(() -> new CustomException(ErrorCode.SPEC_NOT_FOUND));

            // questionIndex 순서로 정렬해서 조회 (findAllByUserId는 정렬을 보장하지 않음)
            List<Experience> experiences = experienceRepository.findAllByUserId(userId).stream()
                    .sorted(Comparator.comparing(Experience::getQuestionIndex))
                    .collect(Collectors.toList());

            String prompt = buildPrompt(companyJd, spec, experiences);

            application.updateStatus(ApplicationStatus.GENERATING);
            String analysisResult = gptResearcherClient.runResearch(prompt);

            application.updateResult(analysisResult, analysisResult);
            application.updateStatus(ApplicationStatus.COMPLETED);

        } catch (Exception e) {
            log.error("AI 자소서 생성 실패 (applicationId={})", application.getApplicationId(), e);
            application.updateStatus(ApplicationStatus.FAILED);
        }
    }

    private String buildPrompt(CompanyJD companyJd, BasicSpec spec, List<Experience> experiences) {
        String experienceText = experiences.stream()
                .map(e -> String.format("%d. %s", e.getQuestionIndex(), e.getAnswerContent()))
                .collect(Collectors.joining("\n"));

        return String.format(
                "다음은 지원자의 스펙과 경험, 목표 기업/직무 정보입니다.\n\n" +
                        "[목표 기업/직무]\n기업명: %s\n직무요건: %s\n우대사항: %s\n인재상: %s\n\n" +
                        "[지원자 기본 스펙]\n학교: %s\n전공: %s\n희망 직무: %s\n어학: %s %s\n\n" +
                        "[지원자 경험 답변]\n%s\n\n" +
                        "[요청사항]\n" +
                        "지원자의 스펙과 경험 중 %s의 %s 직무에 가장 적합한 핵심 역량만 추출하여 " +
                        "이력서용 핵심 요약 문단으로 재구성해 주세요.",
                companyJd.getCompanyName(), companyJd.getRequirements(),
                companyJd.getPreferredSkills(), companyJd.getCoreValue(),
                spec.getSchool(), spec.getMajor(), spec.getDesiredJob(),
                spec.getLanguageName(), spec.getLanguageScore(),
                experienceText,
                companyJd.getCompanyName(), companyJd.getJobName()
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