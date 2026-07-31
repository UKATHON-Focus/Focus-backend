package com.focus.repository;

import com.focus.domain.BasicSpec;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasicSpecRepository extends JpaRepository<BasicSpec, Long> {
    Optional<BasicSpec> findByUserId(Long userId);
}
