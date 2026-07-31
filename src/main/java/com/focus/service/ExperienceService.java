package com.focus.service;

import com.focus.domain.Experience;
import com.focus.domain.User;
import com.focus.dto.request.ExperienceRequest;
import com.focus.dto.response.ExperienceListResponse;
import com.focus.dto.response.ExperienceResponse;
import com.focus.global.exception.CustomException;
import com.focus.global.exception.ErrorCode;
import com.focus.repository.ExperienceRepository;
import com.focus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;

    // [질문 답변 작성]
    @Transactional
    public ExperienceResponse putExperience(Long userId, Long questionIndex, ExperienceRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 해당 질문 번호로 작성된 경험이 있는지 확인 후, 없으면 빈 객체 생성
        Experience experience = experienceRepository.findByUserIdAndQuestionIndex(userId, questionIndex)
                .orElseGet(() -> Experience.builder()
                        .user(user)
                        .questionIndex(questionIndex)
                        .answerContent("")
                        .build());

        experience.updateAnswer(request.answerContent());
        Experience savedExperience = experienceRepository.save(experience);

        return ExperienceResponse.from(savedExperience);
    }

    // [입력한 답변 전체 조회]
    @Transactional(readOnly = true)
    public List<ExperienceListResponse> getAllExperiences(Long userId) {
        List<Experience> experiences = experienceRepository.findAllByUserId(userId);

        return experiences.stream()
                .map(ExperienceListResponse::from)
                .collect(Collectors.toList());
    }
}