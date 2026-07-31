package com.focus.service;

import com.focus.client.GptResearcherClient;
import com.focus.domain.JobPosting;
import com.focus.domain.User;
import com.focus.domain.ResumeAnswer;
import com.focus.repository.JobPostingRepository;
import com.focus.repository.MemberRepository;
import com.focus.repository.ResumeAnswerRepository;
import com.focus.dto.ResumeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final MemberRepository memberRepository;
    private final JobPostingRepository jobPostingRepository;
    private final ResumeAnswerRepository resumeAnswerRepository;
    private final GptResearcherClient gptResearcherClient;

    @Transactional
    public String generateTailoredResume(ResumeRequestDto dto) {
        User user = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        JobPosting jobPosting = jobPostingRepository.findById(dto.getJobPostingId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공고입니다."));

        String prompt = buildPrompt(jobPosting, dto);
        String analysisResult = gptResearcherClient.runResearch(prompt);

        ResumeAnswer resumeAnswer = ResumeAnswer.builder()
                .member(user)
                .jobPosting(jobPosting)
                .conflictResolution(dto.getConflictResolution())
                .roleAndContribution(dto.getRoleAndContribution())
                .hardestPart(dto.getHardestPart())
                .aiExperience(dto.getAiExperience())
                .analyzedResult(analysisResult)
                .build();

        resumeAnswerRepository.save(resumeAnswer);

        return analysisResult;
    }

    private String buildPrompt(JobPosting jobPosting, ResumeRequestDto dto) {
        return String.format(
                "다음은 지원자의 답변과 목표 기업/직무 정보입니다.\n\n" +
                        "[목표 기업/직무]\n기업명: %s\n직무요건 및 인재상: %s\n\n" +
                        "[지원자 답변 내용]\n" +
                        "1. 갈등 해결 경험: %s\n" +
                        "2. 역할 및 기여: %s\n" +
                        "3. 가장 어려웠던 점: %s\n" +
                        "4. 생성형 AI 활용 경험: %s\n\n" +
                        "[요청사항]\n" +
                        "지원자의 경험 중 %s의 %s 직무에 가장 적합한 핵심 역량만 추출하여 이력서용 핵심 요약 문단으로 재구성해 주세요.",
                jobPosting.getCompanyName(), jobPosting.getRequirements(),
                dto.getConflictResolution(), dto.getRoleAndContribution(),
                dto.getHardestPart(), dto.getAiExperience(),
                jobPosting.getCompanyName(), jobPosting.getPosition()
        );
    }
}
