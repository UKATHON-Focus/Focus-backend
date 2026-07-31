package com.focus.repository;

import com.focus.domain.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    Optional<Experience> findByUserIdAndQuestionIndex(Long userId, Long questionIndex);
}
