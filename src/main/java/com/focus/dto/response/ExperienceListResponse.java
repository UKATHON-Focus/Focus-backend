package com.focus.dto.response;

import com.focus.domain.Experience;

public record ExperienceListResponse(
        Long experienceId,
        Long questionIndex,
        String answerContent
) {
    public static ExperienceListResponse from(Experience experience) {
        return new ExperienceListResponse(
                experience.getId(),
                experience.getQuestionIndex(),
                experience.getAnswerContent()
        );
    }
}