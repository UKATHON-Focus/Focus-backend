package com.focus.dto.response;

import com.focus.domain.Experience;

public record ExperienceResponse(
        Long experienceId,
        Long questionIndex
) {
    public static ExperienceResponse from(Experience experience) {
        return new ExperienceResponse(
                experience.getId(),
                experience.getQuestionIndex()
        );
    }
}